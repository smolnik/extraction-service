package net.adamsmolnik.control.extraction;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.adamsmolnik.entity.EntityReference;
import net.adamsmolnik.exceptions.ServiceException;
import net.adamsmolnik.provider.EntitySaver;

/**
 * @author ASmolnik
 *
 */
public class ZipHandler implements ExtractionHandler {

    private static final String OBJECT_KEY_SEPARATOR = "/";

    private static class MutableBoolean {

        private boolean value = false;

    }

    @Override
    public List<String> extract(String objectKey, InputStream is, EntitySaver entitySaver) {
        List<String> objectKeys = new ArrayList<>();
        final MutableBoolean canBeClosed = new MutableBoolean();
        ZipInputStream zis = null;
        try {
            zis = new ZipInputStream(is) {

                public void close() {
                    if (!canBeClosed.value) {
                        return;
                    }
                };
            };
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                try {
                    if (entitySaver != null) {
                        String newEntryObjectKey = buildObjectKey(objectKey, ze.getName());
                        entitySaver.save(new EntityReference(newEntryObjectKey), ze.getSize(), zis);
                        objectKeys.add(newEntryObjectKey);
                    }
                } finally {
                    zis.closeEntry();
                }
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        } finally {
            if (zis != null) {
                canBeClosed.value = true;
                try {
                    zis.close();
                } catch (IOException e) {
                    // deliberately ignored
                }
            }

        }
        return objectKeys;
    }

    private static String buildObjectKey(String objectKey, String entryName) {
        Path objectKeyPath = Paths.get(objectKey);
        String newEntryObjectKey = objectKeyPath.resolveSibling("~" + objectKeyPath.getFileName()).toString() + OBJECT_KEY_SEPARATOR + entryName;
        newEntryObjectKey = newEntryObjectKey.replaceAll(Pattern.quote("\\"), OBJECT_KEY_SEPARATOR);
        return newEntryObjectKey;
    }

}

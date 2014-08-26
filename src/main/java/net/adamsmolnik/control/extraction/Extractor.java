package net.adamsmolnik.control.extraction;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import net.adamsmolnik.entity.Entity;
import net.adamsmolnik.entity.EntityReference;
import net.adamsmolnik.exceptions.ServiceException;
import net.adamsmolnik.provider.EntityProvider;
import net.adamsmolnik.provider.EntitySaver;

/**
 * @author ASmolnik
 *
 */
@Dependent
public class Extractor {

    private static final String LINE_SEPARATOR = "\n";

    @Inject
    private EntityProvider entityProvider;

    @Inject
    private EntitySaver entitySaver;

    private final ConcurrentMap<String, ExtractionHandler> extractionHandlerMap = new ConcurrentHashMap<>();
    {
        extractionHandlerMap.put("zip", new ZipHandler());
    }

    public boolean eligible(String type) {
        return extractionHandlerMap.containsKey(type);
    }

    public ExtractionOutcome extract(String objectKey, String type) {
        Entity entity = entityProvider.getEntity(new EntityReference(objectKey));
        try (InputStream is = entity.getInputStream()) {
            ExtractionHandler eh = extractionHandlerMap.get(type);
            List<String> fileNames = eh.extract(objectKey, is, entitySaver);
            StringBuilder sb = new StringBuilder();
            for (String fn : fileNames) {
                sb.append(fn);
                sb.append(LINE_SEPARATOR);
            }
            String fileNamesAsString = sb.substring(0, sb.length() - LINE_SEPARATOR.length());
            byte[] fileNamesAsBytes = fileNamesAsString.getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream bais = new ByteArrayInputStream(fileNamesAsBytes);
            String extractionInfoObjectKey = objectKey + "-extraction.info";
            entityProvider.persist(new EntityReference(extractionInfoObjectKey), fileNamesAsBytes.length, bais);
            return new ExtractionOutcome(extractionInfoObjectKey, fileNames);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

}

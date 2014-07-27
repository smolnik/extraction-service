package net.adamsmolnik.control.extraction;

import java.io.IOException;
import java.io.InputStream;
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

    public List<String> extract(String objectKey, String type) {
        Entity entity = entityProvider.getEntity(new EntityReference(objectKey));
        try (InputStream is = entity.getInputStream()) {
            ExtractionHandler eh = extractionHandlerMap.get(type);
            return eh.extract(objectKey, is, entitySaver);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

}

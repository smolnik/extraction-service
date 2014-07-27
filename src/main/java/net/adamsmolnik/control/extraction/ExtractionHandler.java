package net.adamsmolnik.control.extraction;

import java.io.InputStream;
import java.util.List;
import net.adamsmolnik.provider.EntitySaver;

/**
 * @author ASmolnik
 *
 */
public interface ExtractionHandler {

    List<String> extract(String objectKey, InputStream is, EntitySaver entitySaver);

}

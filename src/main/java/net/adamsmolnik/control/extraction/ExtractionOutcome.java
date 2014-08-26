package net.adamsmolnik.control.extraction;

import java.util.List;

/**
 * @author ASmolnik
 *
 */
public class ExtractionOutcome {

    private final String extractionInfoObjectKey;

    private final List<String> objectKeys;

    public ExtractionOutcome(String extractionInfoObjectKey, List<String> objectKeys) {
        this.extractionInfoObjectKey = extractionInfoObjectKey;
        this.objectKeys = objectKeys;
    }

    public String getExtractionInfoObjectKey() {
        return extractionInfoObjectKey;
    }

    public List<String> getObjectKeys() {
        return objectKeys;
    }

}

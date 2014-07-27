package net.adamsmolnik.boundary.extraction;

import java.util.Collections;
import java.util.List;
import net.adamsmolnik.control.extraction.Extractor;
import net.adamsmolnik.model.extraction.ExtractionRequest;
import net.adamsmolnik.model.extraction.ExtractionResponse;
import net.adamsmolnik.model.extraction.ExtractionStatus;

/**
 * @author ASmolnik
 *
 */
public abstract class AbstractExtractionService {

    public ExtractionResponse extract(ExtractionRequest extractionRequest) {
        Extractor extractor = getExtractorInstance();
        String type = extractionRequest.type;
        String objKey = extractionRequest.objectKey;
        List<String> emptyList = Collections.emptyList();
        return extractor.eligible(type) ? new ExtractionResponse(ExtractionStatus.EXTRACTED, extractor.extract(objKey, type))
                : new ExtractionResponse(ExtractionStatus.NOT_ELIGIBLE, emptyList);
    }

    protected abstract Extractor getExtractorInstance();

}

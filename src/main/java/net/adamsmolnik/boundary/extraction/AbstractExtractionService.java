package net.adamsmolnik.boundary.extraction;

import net.adamsmolnik.control.extraction.ExtractionOutcome;
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
        if (extractor.eligible(type)) {
            ExtractionOutcome eo = extractor.extract(objKey, type);
            return new ExtractionResponse(ExtractionStatus.EXTRACTED, eo.getExtractionInfoObjectKey(), eo.getObjectKeys());
        } else {
            return new ExtractionResponse(ExtractionStatus.NOT_ELIGIBLE);
        }
    }

    protected abstract Extractor getExtractorInstance();

}

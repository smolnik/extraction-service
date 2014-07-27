package net.adamsmolnik.boundary.extraction;

import net.adamsmolnik.control.extraction.Extractor;

/**
 * @author ASmolnik
 *
 */
public class ExtractionActivityImpl extends AbstractExtractionService implements ExtractionActivity {

    private final Extractor extractor;

    public ExtractionActivityImpl(Extractor extractor) {
        this.extractor = extractor;
    }

    @Override
    protected Extractor getExtractorInstance() {
        return extractor;
    }

}

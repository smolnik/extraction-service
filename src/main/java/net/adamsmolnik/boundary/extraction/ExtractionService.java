package net.adamsmolnik.boundary.extraction;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.adamsmolnik.control.extraction.Extractor;
import net.adamsmolnik.model.extraction.ExtractionRequest;
import net.adamsmolnik.model.extraction.ExtractionResponse;
import net.adamsmolnik.model.extraction.ExtractionStatus;

/**
 * @author ASmolnik
 *
 */
@Path("/es")
@RequestScoped
public class ExtractionService extends AbstractExtractionService {

    @Inject
    private Extractor extractor;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("extract")
    public String extract(@FormParam("objectKey") String objectKey, @FormParam("type") String type) {
        return extractor.eligible(type) ? extractor.extract(objectKey, type).toString() : ExtractionStatus.NOT_ELIGIBLE.toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("extract")
    public ExtractionResponse extract(ExtractionRequest extractionRequest) {
        return super.extract(extractionRequest);
    }

    @Override
    protected Extractor getExtractorInstance() {
        return extractor;
    }

}

package net.adamsmolnik.setup.extraction;

import javax.inject.Singleton;
import net.adamsmolnik.setup.ServiceNameResolver;

/**
 * @author ASmolnik
 *
 */
@Singleton
public class ExtractionServiceNameResolver implements ServiceNameResolver {

    @Override
    public String getServiceName() {
        return "extraction-service";
    }

}

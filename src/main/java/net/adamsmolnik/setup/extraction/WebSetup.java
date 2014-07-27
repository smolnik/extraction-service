package net.adamsmolnik.setup.extraction;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import net.adamsmolnik.boundary.extraction.ExtractionActivityImpl;
import net.adamsmolnik.control.extraction.Extractor;
import net.adamsmolnik.setup.ActivityLauncher;

/**
 * @author ASmolnik
 *
 */
@WebListener("extractionActivitySetup")
public class WebSetup implements ServletContextListener {

    @Inject
    private Extractor extractor;

    @Inject
    private ActivityLauncher al;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        al.register(new ExtractionActivityImpl(extractor));
        al.launch();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        al.shutdown();
    }

}

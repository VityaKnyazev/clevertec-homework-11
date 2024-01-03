package ru.clevertec.knyazev.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.knyazev.datasource.managing.DatabaseManager;
import ru.clevertec.knyazev.datasource.managing.exception.DatabaseManagerException;

@WebListener
@Slf4j
public class AppContextListener implements ServletContextListener {
    public static final String CONTEXT = "applicationContext";

    private final AnnotationConfigApplicationContext applicationContext;

    public AppContextListener() {
        applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loadDataToDataSource();

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(CONTEXT, applicationContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        applicationContext.close();

        ServletContextListener.super.contextDestroyed(sce);
    }

    /**
     * Load data (fill database tables with start-up data) using
     * DatabaseManager depends on DataSourceManagementProperties.
     * If something wnt wrong when writing data to datasource -
     * logging DatabaseManagerException.
     */
    private void loadDataToDataSource() {
        DataSourceManagementProperties dataSourceManagementProperties =
                applicationContext.getBean(DataSourceManagementProperties.class);

        if (dataSourceManagementProperties.initOnStartup()) {
            DatabaseManager liquibaseDatabaseManager = applicationContext.getBean(DatabaseManager.class);
            try {
                liquibaseDatabaseManager.loadData();
            } catch (DatabaseManagerException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}

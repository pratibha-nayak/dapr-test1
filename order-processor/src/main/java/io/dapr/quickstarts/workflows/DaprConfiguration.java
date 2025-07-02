package io.dapr.quickstarts.workflows;

import io.dapr.workflows.client.DaprWorkflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaprConfiguration {

    /**
     * Creates a singleton DaprWorkflowClient bean that will be managed by Spring.
     * The Dapr SDK automatically uses environment variables to connect to the sidecar.
     * @return A configured DaprWorkflowClient.
     */
    @Bean
    public DaprWorkflowClient daprWorkflowClient() {
        // The client is AutoCloseable, and Spring will automatically manage
        // its lifecycle, calling close() on application shutdown.
        return new DaprWorkflowClient();
    }
}
package io.dapr.quickstarts.workflows;


import io.dapr.quickstarts.workflows.models.OrderPayload;
import io.dapr.workflows.client.DaprWorkflowClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class WorkflowController {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowController.class);

    // 1. Inject the DaprWorkflowClient. Spring will provide the correctly configured bean.
    private final DaprWorkflowClient daprWorkflowClient;

    // 2. Use constructor injection. This is a best practice for required dependencies.
    public WorkflowController(DaprWorkflowClient daprWorkflowClient) {
        this.daprWorkflowClient = daprWorkflowClient;
    }

    @GetMapping("/getOrder")
    public String getOrder() {
        return "GET was successful";
    }
    /**
     * Handles the submission of a new order.
     * This endpoint is asynchronous: it schedules the workflow and returns immediately.
     *
     * @param order The order details from the request body.
     * @return A ResponseEntity indicating the workflow has been accepted for processing.
     */

    @PostMapping("/submit")
    public ResponseEntity<String> submitOrder(@RequestBody OrderPayload order) {
        // 3. The controller's job is simple: receive the request, validate (if needed),
        //    and schedule the workflow. It should be fast and non-blocking.
        String instanceId = "order_" + UUID.randomUUID().toString().substring(0, 8);
        logger.info("Received order for {} of item '{}'. Scheduling workflow with instance ID: {}",
                order.getQuantity(), order.getItemName(), instanceId);

        try {
            // 4. Use the injected client to schedule the workflow. This is an async call.
            daprWorkflowClient.scheduleNewWorkflow(OrderProcessingWorkflow.class, order, instanceId);

            String responseMessage = "Workflow accepted for processing with instance ID: " + instanceId;
            logger.info(responseMessage);

            // 5. Return a 202 ACCEPTED status. This is the correct HTTP response for
            //    starting a long-running, asynchronous operation.
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);

        } catch (Exception e) {
            logger.error("Error scheduling workflow with instance ID '{}'", instanceId, e);
            return ResponseEntity.internalServerError().body("Failed to start workflow: " + e.getMessage());
        }
    }
}
//package io.dapr.quickstarts.workflows;
//
//import java.time.Duration;
//import org.slf4j.Logger;
//
//import io.dapr.workflows.Workflow;
//import io.dapr.workflows.WorkflowStub;
//
//public class FakeOrderProcessingWorkflow  implements Workflow {
////
//        @Override
//        public WorkflowStub create() {
//            return ctx -> {
//                Logger logger = ctx.getLogger();
//                String orderId = ctx.getInstanceId();
//                logger.info("Starting Workflow: " + ctx.getName());
//                logger.info("Instance ID(order ID): " + orderId);
//                logger.info("Current Orchestration Time: " + ctx.getCurrentInstant());
//
//                String order = ctx.getInput(String.class);
//                logger.info("Received Order: " + order.toString());
//                String orderResult = "resulting string";
//
//                String inventoryResult = ctx.callActivity(FakeActivity.class.getName(),
//                        "input from client:abc", String.class).await();
//                ctx.complete(orderResult);
//                // Complete the workflow with order result is processed
////                orderResult.setProcessed(true);
//                ctx.complete(orderResult);
//                return;
//            };
//        }
//
//    }
//

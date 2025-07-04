///*
// * Copyright 2023 The Dapr Authors
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *     http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
//*/
//
//package io.dapr.quickstarts.workflows;
//
//import io.dapr.client.DaprClient;
//import io.dapr.client.DaprClientBuilder;
//import io.dapr.quickstarts.workflows.activities.*;
//import io.dapr.quickstarts.workflows.models.InventoryItem;
//import io.dapr.quickstarts.workflows.models.OrderPayload;
//import io.dapr.workflows.client.DaprWorkflowClient;
//import io.dapr.workflows.client.WorkflowInstanceStatus;
//import io.dapr.workflows.runtime.WorkflowRuntime;
//import io.dapr.workflows.runtime.WorkflowRuntimeBuilder;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import java.time.Duration;
//import java.util.concurrent.TimeoutException;
//
//
//public class WorkflowConsoleApp_backup {
//
//  private static final String STATE_STORE_NAME = "statestore";
//
//  /**
//   * The main method of this console app.
//   *
//   * @param args The port the app will listen on.
//   * @throws Exception An Exception.
//   */
//  public static void main(String[] args) throws Exception {
//    System.out.println("*** Welcome to the Dapr Workflow console app sample!");
//    System.out.println("*** Using this app, you can place orders that start workflows.");
//    // Wait for the sidecar to become available
//    Thread.sleep(5 * 1000);
//
//    // Register the OrderProcessingWorkflow and its activities with the builder.
//    WorkflowRuntimeBuilder builder = new WorkflowRuntimeBuilder().registerWorkflow(OrderProcessingWorkflow.class);
//    builder.registerActivity(NotifyActivity.class);
//    builder.registerActivity(ProcessPaymentActivity.class);
//    builder.registerActivity(RequestApprovalActivity.class);
//    builder.registerActivity(VerifyInventoryActivity.class);
//    builder.registerActivity(UpdateInventoryActivity.class);
//
//    // Build and then start the workflow runtime pulling and executing tasks
//    WorkflowRuntime runtime = builder.build();
//    System.out.println("Start workflow runtime>>>>>>>>>>>>>");
//    runtime.start(false);
//
//    InventoryItem inventory = prepareInventoryAndOrder();
////
////    DaprWorkflowClient workflowClient = new DaprWorkflowClient();
////    try (workflowClient) {
////      executeWorkflow(workflowClient, inventory);
////    }
//  }
//
//  private static void executeWorkflow(DaprWorkflowClient workflowClient, InventoryItem inventory) {
//    System.out.println("==========Begin the purchase of item:==========");
//    String itemName = inventory.getName();
//    int orderQuantity = inventory.getQuantity();
//    int totalCost = orderQuantity * inventory.getPerItemCost();
//    OrderPayload order = new OrderPayload();
//    order.setItemName(itemName);
//    order.setQuantity(orderQuantity);
//    order.setTotalCost(totalCost);
//    System.out.println("Starting order workflow, purchasing " + orderQuantity + " of " + itemName);
//
//    String instanceId = workflowClient.scheduleNewWorkflow(OrderProcessingWorkflow.class, order);
//    System.out.printf("Scheduled new workflow instance of OrderProcessingWorkflow with instance ID: %s%n",
//        instanceId);
//
//    try {
//      workflowClient.waitForInstanceStart(instanceId, Duration.ofSeconds(10), false);
//      System.out.printf("Workflow instance %s started%n", instanceId);
//    } catch (TimeoutException e) {
//      System.out.printf("Workflow instance %s did not start within 10 seconds%n", instanceId);
//      return;
//    }
//
//    try {
//      WorkflowInstanceStatus workflowStatus = workflowClient.waitForInstanceCompletion(instanceId,
//          Duration.ofSeconds(30),
//          true);
//      if (workflowStatus != null) {
//        System.out.printf("Workflow instance completed, out is: %s%n",
//            workflowStatus.getSerializedOutput());
//      } else {
//        System.out.printf("Workflow instance %s not found%n", instanceId);
//      }
//    } catch (TimeoutException e) {
//      System.out.printf("Workflow instance %s did not complete within 30 seconds%n", instanceId);
//    }
//
//  }
//
//  private static InventoryItem prepareInventoryAndOrder() {
//    // prepare 10 cars in inventory
//    InventoryItem inventory = new InventoryItem();
//    inventory.setName("cars");
//    inventory.setPerItemCost(5000);
//    inventory.setQuantity(10);
//    DaprClient daprClient = new DaprClientBuilder().build();
//    restockInventory(daprClient, inventory);
//
//    // prepare order for 1 car
//    InventoryItem order = new InventoryItem();
//    order.setName("cars");
//    order.setPerItemCost(5000);
//    order.setQuantity(1);
//    return order;
//  }
//
//  private static void restockInventory(DaprClient daprClient, InventoryItem inventory) {
//    String key = inventory.getName();
//    daprClient.saveState(STATE_STORE_NAME, key, inventory).block();
//  }
//}

/*
 * Copyright 2023 The Dapr Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package io.dapr.quickstarts.workflows;
import io.dapr.quickstarts.workflows.activities.NotifyActivity;
import io.dapr.quickstarts.workflows.activities.ProcessPaymentActivity;
import io.dapr.quickstarts.workflows.activities.RequestApprovalActivity;
import io.dapr.quickstarts.workflows.activities.VerifyInventoryActivity;
import io.dapr.quickstarts.workflows.activities.UpdateInventoryActivity;

import io.dapr.workflows.runtime.WorkflowRuntime;
import io.dapr.workflows.runtime.WorkflowRuntimeBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorkflowConsoleApp {

  private static final String STATE_STORE_NAME = "statestore";


  public static void main(String[] args) {
    // We don't need the Spring context, just a way to start the Dapr runtime
    // and keep the process alive. Spring Boot is convenient for this.
    SpringApplication.run(WorkflowConsoleApp.class, args);
  }


  @Bean
  public WorkflowRuntime startDaprWorkflowRuntime(
          VerifyInventoryActivity checkInventoryActivity,
          ProcessPaymentActivity processPaymentActivity,
          NotifyActivity notifyActivity) {
//      System.out.println("going to sleeepppp>>>");
    // Wait for the sidecar to become available
//      try {
////          Thread.sleep(5 * 1000);
//      } catch (InterruptedException e) {
//          throw new RuntimeException(e);
//      }

      // Register the OrderProcessingWorkflow and its activities with the builder.
    WorkflowRuntimeBuilder builder = new WorkflowRuntimeBuilder().registerWorkflow(OrderProcessingWorkflow.class);
    builder.registerActivity(NotifyActivity.class);
    builder.registerActivity(ProcessPaymentActivity.class);
    builder.registerActivity(RequestApprovalActivity.class);
    builder.registerActivity(VerifyInventoryActivity.class);
    builder.registerActivity(UpdateInventoryActivity.class);

    // Build and then start the workflow runtime pulling and executing tasks
    WorkflowRuntime runtime = builder.build();
    System.out.println("Start workflow runtime>>>>>>>>>>>>>");
    runtime.start(false);
   System.out.println("workflow runtime STARTED>>>>>>>>>>>>>");
    return runtime;
  }
}

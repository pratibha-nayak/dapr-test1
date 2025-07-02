package io.dapr.quickstarts.workflows;

import io.dapr.workflows.WorkflowActivity;
import io.dapr.workflows.WorkflowActivityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakeActivity  implements WorkflowActivity {
    private static Logger logger = LoggerFactory.getLogger(io.dapr.quickstarts.workflows.activities.ProcessPaymentActivity.class);

        @Override
        public Object run(WorkflowActivityContext ctx) {
            String req = ctx.getInput(String.class);
            logger.info("Processing payment: {} ", req);

            // Simulate slow processing
            try {
                Thread.sleep(7 * 1000);
            } catch (InterruptedException e) {
            }
            logger.info("Payment for request ID '{}' processed successfully", req);

            return true;
        }

    }
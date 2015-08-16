package org.apache.ambari;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Random;

public class MetricProducer {

    static final String METRIC = "{\n" +
            "    \"metrics\": [\n" +
            "      {\n" +
            "          \"metricname\": \"FakeMetric\",\n" +
            "          \"appid\": \"amssmoketestfake\",\n" +
            "          \"hostname\": \"localhost\",\n" +
            "          \"timestamp\": %s,\n" +
            "          \"starttime\": %s,\n" +
            "          \"metrics\": {\n" +
            "            \"%s\": %s" +
            "          }\n" +
            "        }\n" +
            "        ]\n" +
            "}";

    public static void main(String[] args) {
        long delay = 5000;
        int numMetrics = 100;
        String url = "http://localhost:6188/ws/v1/timeline/metrics";

        if (args.length == 3) {
            url = args[0];
            numMetrics = Integer.valueOf(args[1]);
            delay = Long.valueOf(args[2]);
        }

        final Random random = new Random();
        final Client client = ClientBuilder.newClient();

        for (int i=0; i < numMetrics; i++) {
            int value = random.nextInt(10);
            long currTime = System.currentTimeMillis();

            final String metric = String.format(METRIC, currTime, currTime, currTime, value);

            final WebTarget metricsTarget = client.target(url);
            final Invocation.Builder invocation = metricsTarget.request();

            final Entity<String> entity = Entity.json(metric);
            final Response response = invocation.post(entity);
            System.out.println("Status for metric # " + i + " : " + response.getStatus());

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

# ambari-metric-producer
Test program to send metrics to Ambari Metrics Collector

## Quick Start

    mvn clean install
    java -jar target/amabri-metric-producer-1.0-SNAPSHOT.jar

The default parameters are:

* Metrics Collector URL - http://localhost:6188/ws/v1/timeline/metrics
* Number of Metrics - 100
* Delay Between Metrics - 5000 ms

Override these parameters by passing URL NUM_METRICS DELAY to the main method.

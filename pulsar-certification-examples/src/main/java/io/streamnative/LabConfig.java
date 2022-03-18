package io.streamnative;

import java.util.Optional;

public class LabConfig {
    public static final String SERVICE_URL                  = "pulsar://localhost:6650";
    public static final String ADMIN_SERVICE_URL            = "http://localhost:8080";

    public static final String TEST_TOPIC                   = "test-topic";

    public static final Optional<String> AUTH_TOKEN         = Optional.empty();

}

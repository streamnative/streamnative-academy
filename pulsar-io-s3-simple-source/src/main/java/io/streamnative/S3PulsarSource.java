package io.streamnative;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.pulsar.io.core.PushSource;
import org.apache.pulsar.io.core.SourceContext;
import org.apache.pulsar.io.core.annotations.Connector;
import org.apache.pulsar.io.core.annotations.IOType;
import org.slf4j.Logger;

@Connector(
        name = "S3 Source Connector",
        type = IOType.SOURCE,
        help = "Simple S3 Source Connector Example",
        configClass = S3SourceConfig.class
)
public class S3PulsarSource extends PushSource<String> {
    private static Logger logger;
    private static AmazonS3 awsS3client;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Override
    public void open(Map<String, Object> config, SourceContext sourceContext) throws Exception {
        logger = sourceContext.getLogger();
        logger.info("Starting AWS S3 Source...");

        logger.info("Loaded config ...");
        S3SourceConfig s3SourceConfig = S3SourceConfig.load(config);
        s3SourceConfig.validate();

        String accessKey    = sourceContext.getSecret("AWS_ACCESS_KEY");
        String accessSecret = sourceContext.getSecret("AWS_SECRET_KEY");

        initS3Client(accessKey, accessSecret, s3SourceConfig.getAwsRegion());

        // create a consumer Thread foreach bucket
        String[] buckets = s3SourceConfig.getAwsBuckets().split(",", -1);

        for (String bucket: buckets) {
            S3WorkerThread s3WorkerThread = new S3WorkerThread(this, sourceContext, awsS3client, bucket, logger);
            executorService.submit(s3WorkerThread);
        }
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(3000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
                // wait a while for tasks to respond to being cancelled
                executorService.awaitTermination(3000, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    private void initS3Client(String accessKey, String accessSecret, String awsRegion) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        awsS3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsRegion)
                .build();
    }
}

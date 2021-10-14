package io.streamnative;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.pulsar.io.core.PushSource;
import org.apache.pulsar.io.core.SourceContext;
import org.slf4j.Logger;

public class S3WorkerThread extends Thread {
    private final Logger logger;
    private final AmazonS3 awsS3client;
    private final String bucket;

    private final SourceContext sourceContext;
    private PushSource<String> source;

    public S3WorkerThread(PushSource<String> source,
                          SourceContext sourceContext,
                          AmazonS3 awsS3client,
                          String bucket,
                          Logger logger) {
        this.source = source;
        this.sourceContext = sourceContext;
        this.awsS3client = awsS3client;
        this.bucket = bucket;
        this.logger = logger;
    }

    @Override
    public void run() {
        logger.info(String.format("Scanning bucket '%s' for files.", bucket));

        ObjectListing objectListing = awsS3client.listObjects(bucket);
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            logger.info(String.format("Processing file %s", os.getKey()));
            consumeFile(os.getKey());
        }
    }

    private void consumeFile(String fileName) {
        logger.info(String.format("Consuming file %s to topic %s.", fileName, sourceContext.getOutputTopic()));
        try {
            List<String> lines = loadFileFromS3(fileName);
            AtomicInteger counter = new AtomicInteger(0);

            for (String line: lines) {
                Map<String, String> properties = new HashMap<>();
                properties.put("file", fileName);
                properties.put("line_number", counter.getAndIncrement() + "");
                S3FileRecord record =
                        new S3FileRecord(Optional.empty(), line, properties, Optional.of(System.currentTimeMillis()));
                source.consume(record);
                record.ack();
            }

            logger.info(String.format("Processed %d lines from %s", counter.get(), fileName));
        } catch (Exception e) {
            logger.error("Filed while processing file '" + fileName + "'.:", e);
        }
    }

    private List<String> loadFileFromS3(String FILE_NAME) {
        try (final S3Object s3Object = awsS3client.getObject(bucket, FILE_NAME);
             final InputStreamReader streamReader = new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8);
             final BufferedReader reader = new BufferedReader(streamReader)) {
            return reader.lines().collect(Collectors.toList());
        } catch (final IOException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}

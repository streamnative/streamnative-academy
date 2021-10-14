package io.streamnative;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class S3Utils {
    private static AmazonS3 s3client;

    public static void main(String[] args) {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIARCBCSD4BMCF7IMXP",
                "l9a/kTiaamq6sUnIM5VgV+M4Ii7GxxFt8wfmUeMh"
        );

        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_2)
                .build();

        String eventsBucket     = "events-bucket-small";
        String productsBucket   = "products-bucket-small";
        String usersBucket      = "users-bucket-small";

        createBucket(eventsBucket);
        createBucket(productsBucket);
        createBucket(usersBucket);

        listBuckets();

        uploadFile(
                eventsBucket, "events.csv", "datasets/events.csv"
        );

        uploadFile(
                productsBucket, "products.csv", "datasets/products.csv"
        );

        uploadFile(
                usersBucket, "users.csv", "datasets/users.csv"
        );

        System.out.println();
        System.out.println();

        listObjectsInBucket(eventsBucket);
        listObjectsInBucket(productsBucket);
        listObjectsInBucket(usersBucket);

//        String[] objkeyArr = {
//                "productinfo.csv",
//                "userinfo.csv",
//                "events.csv"
//        };
//
//        deleteObject("userbehavior-bucket-small", objkeyArr);
//        deleteBucket("userbehavior-bucket-small");
    }

    private static void createBucket(String bucketName) {
        try {
            s3client.createBucket(bucketName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listBuckets() {
        System.out.println("Available Buckets:");
        List<Bucket> buckets = s3client.listBuckets();
        for(Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }
    }

    private static void uploadFile(String bucketName, String fileName, String inputFilePath) {
        s3client.putObject(
                bucketName,
                fileName,
                new File(inputFilePath)
        );
    }

    private static void deleteBucket(String bucketName) {
        // delete a bucket
        try {
            s3client.deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }

    private static void deleteObject(String bucketName, String[] objkeyArr) {
        DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(bucketName)
                .withKeys(objkeyArr);
        s3client.deleteObjects(delObjReq);

    }

    private static void listObjectsInBucket(String bucketName) {
        System.out.println("\nListing Objects:");
        ObjectListing objectListing = s3client.listObjects(bucketName);
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            System.out.println(os.getKey());

            loadFileFromS3(s3client, bucketName, os.getKey())
                    .stream().limit(10)
                    .forEach(System.out::println);

        }
    }

    private static List<String> loadFileFromS3(AmazonS3 s3client, String BUCKET_NAME, String FILE_NAME) {
        try (final S3Object s3Object = s3client.getObject(BUCKET_NAME, FILE_NAME);
             final InputStreamReader streamReader = new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8);
             final BufferedReader reader = new BufferedReader(streamReader)) {
            return reader.lines().collect(Collectors.toList());
        } catch (final IOException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}

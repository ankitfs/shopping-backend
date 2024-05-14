package com.ankit.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class S3ClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(S3ClientFactory.class);

    public S3Client getS3Client() throws Exception{
        ProfileCredentialsProvider profileCredentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.AP_SOUTH_1;
        return S3Client.builder().region(region).credentialsProvider(profileCredentialsProvider).build();
    }

    public String uploadImageToS3(String bucketName, MultipartFile imageMultiPart) throws Exception {

        //S3 URL Pattern: s3://<bucket-name>/<filename>
        String s3ImagePath = "s3://"+bucketName+"/"+imageMultiPart.getOriginalFilename();
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", "image/jpeg");
        String fileName = imageMultiPart.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder().
                                    bucket(bucketName).
                                    key(fileName).
                                    metadata(metadata).
                                    build();

        S3Client s3Client = getS3Client();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageMultiPart.getBytes()));


        return fileName;
    }

    public void deleteImageFromS3(String bucketName, String fileName) throws Exception {

        S3Client s3Client = getS3Client();

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                                                    .bucket(bucketName)
                                                    .key(fileName)
                                                    .build();
        DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest);

        logger.debug(deleteObjectResponse.toString());
    }
}

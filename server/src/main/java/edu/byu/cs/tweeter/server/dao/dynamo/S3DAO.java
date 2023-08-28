package edu.byu.cs.tweeter.server.dao.dynamo;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.model.TrimmedDataAccessException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class S3DAO {

    private static final String BUCKET_NAME = "tweeters3bucket";

    public String upload(String alias, String encodedBase64Image) {
        URL url = null;
        try {
            System.out.println("Enter s3 class tp upload");
            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withRegion("us-west-2")
                    .build();

            String fileName = String.format("%s_profile_image", alias);

            // Get image bytes.
            System.out.println("Getting image bites");
            byte[] imageBytes = Base64.getDecoder().decode(encodedBase64Image);

            // Set image metadata.
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageBytes.length);
            metadata.setContentType("image/jpeg");

            System.out.println("Puting the Object request");
            PutObjectRequest fileRequest = new PutObjectRequest(BUCKET_NAME, fileName, new ByteArrayInputStream(imageBytes), metadata);

            System.out.println("s3.putObject");

            s3.putObject(fileRequest);
            url = s3.getUrl(BUCKET_NAME, fileName);
           // url = new URL("https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

            System.out.println("Got the URL for the image");
        } catch (AmazonServiceException e) {
            throw new TrimmedDataAccessException("[Server Error] Unable to upload image to s3." + e.getMessage());
        }
        return url.toString();
    }
}

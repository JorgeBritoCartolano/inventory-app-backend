package inventory.service.impl;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import inventory.service.S3Service;
import java.io.File;
import org.springframework.stereotype.Service;

@Service
public class S3ServiceImpl implements S3Service {

  public void uploadFile(File file, String bucketName, String key) {

    AmazonS3 s3 =
        AmazonS3ClientBuilder.standard()
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .withRegion(Regions.EU_WEST_3)
            .build();

    s3.putObject(bucketName, key, file);
  }
}

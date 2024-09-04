package inventory.service;

import java.io.File;

public interface S3Service {

  void uploadFile(File file, String bucketName, String key);
}

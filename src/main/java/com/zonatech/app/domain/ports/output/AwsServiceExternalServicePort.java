package com.zonatech.app.domain.ports.output;

import java.nio.file.Path;
import java.time.Duration;

public interface AwsServiceExternalServicePort {
    Boolean uploadFileS3 (String bucketName, String key, Path filePath);
    String getPresignedUrl (String bucketName, String key, Duration duration);
}

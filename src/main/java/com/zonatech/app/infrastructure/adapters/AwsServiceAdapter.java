package com.zonatech.app.infrastructure.adapters;

import com.zonatech.app.domain.ports.output.AwsServiceExternalServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class AwsServiceAdapter implements AwsServiceExternalServicePort {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Override
    public Boolean uploadFileS3(String bucketName, String key, Path filePath) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            // Convertir el Path a RequestBody
            RequestBody requestBody = RequestBody.fromFile(filePath);

            PutObjectResponse putObjectResponse = this.s3Client.putObject(putObjectRequest, requestBody);
            return putObjectResponse.sdkHttpResponse().isSuccessful();

        } catch (Exception e) {
            throw new RuntimeException("Error al subir archivo a S3", e);
        }
    }

    @Override
    public String getPresignedUrl(String bucketName, String key, Duration duration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(duration) // Ej: 1 hora
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
        URL presignedUrl = presignedRequest.url();
        return presignedUrl.toString();
    }
}

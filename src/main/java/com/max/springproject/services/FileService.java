package com.max.springproject.services;
;
import com.max.springproject.models.File;
import com.max.springproject.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService implements GenericService<File> {
    private FileRepository repository;
    private S3Client client;
    private String bucketName = "spring-project-bucket1";
    private Charset utf8Charset = Charset.forName("UTF-8");
    private GetObjectRequest request;
    private ResponseInputStream<GetObjectResponse> response;
    private String filePath = "src/main/resources/files/";

    @Autowired
    public FileService(FileRepository repository) {
        this.repository = repository;
        client = S3Client.builder().region(Region.EU_NORTH_1).build();
    }

    @Override
    public File getById(long id) {
        File file = repository.getById(id);
        request = GetObjectRequest.builder().bucket(bucketName).key(file.getName()).build();
        ResponseInputStream<GetObjectResponse> response = client.getObject(request);
        GetObjectResponse objectResponse = response.response();
        java.io.File file1 = new java.io.File(filePath + file.getName());
        try(OutputStream outputStream = new FileOutputStream(file1)) {
            outputStream.write(response.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public List<File> getAll() {
        List<File> files = repository.findAll();
        for (var file :
                files) {
            request = GetObjectRequest.builder().bucket(bucketName).key(file.getName()).build();
            response = client.getObject(request);
            try {
                file.setContent(new String(response.readAllBytes(), utf8Charset));
            } catch (IOException e) {
                file.setContent("no saved data");
            }
        }
        return files;
    }

    @Override
    public File save(File object) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().
                bucket(bucketName).
                key(object.getName()).
                build();
        Path path = Paths.get(filePath + object.getName());
        client.putObject(putObjectRequest, path);
        java.io.File file = new java.io.File(filePath + object.getName());
        file.delete();

        return repository.save(object);
    }

    @Override
    public void deleteById(long id) {
        File file = repository.getById(id);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().
                bucket(bucketName).
                key(file.getName())
                .build();
        client.deleteObject(deleteObjectRequest);
        repository.deleteById(id);
    }
}
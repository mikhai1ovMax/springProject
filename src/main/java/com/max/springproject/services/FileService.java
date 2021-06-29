package com.max.springproject.services;

import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
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


import java.io.*;
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
        setRepository(repository);
        setS3Client(S3Client.builder().region(Region.EU_NORTH_1).build());
    }

    public void setRepository(FileRepository repository){
        this.repository = repository;
    }

    public void setS3Client(S3Client client){
        this.client = client;
    }

    @Override
    public List<File> getAll() {
        return repository.findAll();
    }


    @Override
    public File getById(long id) {
        File file = repository.getById(id);
        return file;
    }


    @Override
    public File save(File object) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(object.getName())
                .build();
        Path path = Paths.get(filePath + object.getName());
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        client.putObject(putObjectRequest, path).bucketKeyEnabled();
        path.toFile().delete();
        return repository.save(object);
    }

//    @Override
//    public File save(File object) {
//        return null;
//    }

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

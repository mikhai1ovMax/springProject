package com.max.springproject.services;

import com.max.springproject.models.File;
import com.max.springproject.repositories.FileRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FileServiceTest {
//    @Rule
//    public MockitoRule rule = MockitoJUnit.rule();

    private FileRepository repository = Mockito.mock(FileRepository.class);
    private S3Client client = Mockito.mock(S3Client.class);

    FileService service;

    public FileServiceTest() {
        service = new FileService(repository);
        service.setS3Client(client);
    }

    @Test
    public void getById() {
        File file = new File(1, "test", "xyz.txt");
        Mockito.when(repository.getById(1l)).thenReturn(file);
        assertEquals(file, service.getById(1l));
    }

    @Test
    public void getAll() {
        List<File> files = Arrays.asList(
                new File(1, "test", "qwe.docx"),
                new File(1, "test2", "test.txt"),
                new File(1, "test3", "qwer.docx")
        );
        Mockito.when(repository.findAll()).thenReturn(files);
        assertEquals(files, service.getAll());
    }

//    @Test
//    void save() {
//        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                .bucket("test")
//                .key("test")
//                .build();
//        Path path = Paths.get("test");
//        File file = new File();
//        PutObjectResponse response = PutObjectResponse.builder().build();
//        Mockito.when(client.putObject(putObjectRequest, path)).thenReturn(response);
//        Mockito.when(repository.save(file)).thenReturn(file);
//        assertEquals(file, service.save(file));
//    }

    @Test
    public void deleteById() {
        DeleteObjectRequest deleteObjectRequest =  DeleteObjectRequest.builder()
                .bucket("test")
                .key("test")
                .build();
        Mockito.when(client.deleteObject(deleteObjectRequest)).thenReturn(null);
        repository.deleteById(2l);
        Mockito.verify(repository, Mockito.times(1)).deleteById(2l);
    }

}

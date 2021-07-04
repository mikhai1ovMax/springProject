package com.max.springproject.controllers;

import com.max.springproject.models.File;
import com.max.springproject.services.FileService;
import com.max.springproject.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class FileControllerTest {


    private FileService service = Mockito.mock(FileService.class);
    private FileController controller =  new FileController(service);
    private File file = new File(123, "test", "test");
    private List<File> files = Arrays.asList(file);

    @Test
    void getFiles() {
        Mockito.when(service.getAll()).thenReturn(files);
        assertEquals(file.getLocation(), controller.getFiles().get(0));
    }

    @Test
    void getById() {
        Mockito.when(service.getById(1l)).thenReturn(file);
        assertEquals(file.getLocation(), controller.getById(1l));
    }

    @Test
    void setById() {
        Mockito.when(service.getAll()).thenReturn(files);
        assertEquals(file.getLocation(), controller.getFiles().get(0));
    }

    @Test
    void deleteEvent() {
        controller.deleteEvent(2l);
        Mockito.verify(service, Mockito.times(1)).deleteById(2l);
    }
}
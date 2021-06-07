package com.max.springproject.controllers;

import com.max.springproject.models.File;
import com.max.springproject.services.FileService;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


@RestController
public class FileController {
    private final FileService service;
    private String filePath = "src/main/resources/files/";


    @Autowired
    public FileController(FileService service) {
        this.service = service;
    }

    //TODO
//    @GetMapping("/files")
//    public List<FileSystemResource> getFiles(){
//        List<FileSystemResource> fileSystemResources = new ArrayList<>();
//        List<File> files = service.getAll();
//        for (var file :
//                files) {
//            fileSystemResources.add(new FileSystemResource(new java.io.File(filePath + file.getName())));
//        }
//        return fileSystemResources;
//    }

    @GetMapping("/files/{id}")
    public FileSystemResource getById(@PathVariable("id") long id){
        File file = service.getById(id);
        java.io.File savedFile = new java.io.File(filePath + file.getName());
        FileSystemResource resource = new FileSystemResource(savedFile);
        return new FileSystemResource(savedFile);
    }

    @PostMapping("/files")
    public String getById(@RequestParam("file") MultipartFile file){
        File object = new File();
        object.setName(file.getOriginalFilename());
        java.io.File savedFile = new java.io.File(filePath + file.getOriginalFilename());
        try(OutputStream outputStream = new FileOutputStream(savedFile)) {
            outputStream.write(file.getInputStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        service.save(object);
        return service.getAll().toString();
    }


    @DeleteMapping("/files/{id}")
    public String deleteEvent(@PathVariable("id") long id) {
        service.deleteById(id);
        return service.getAll().toString();
    }
}

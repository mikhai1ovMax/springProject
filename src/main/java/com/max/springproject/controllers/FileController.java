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
@RequestMapping("/api/v1/files")
public class FileController {
    private final FileService service;
    private String filePath = "src/main/resources/files/";
    private java.io.File savedFile;


    @Autowired
    public FileController(FileService service) {
        this.service = service;
    }

    @GetMapping("/files")
    public List<String> getFiles(){
        List<String> links = new ArrayList<>();
        for (File file : service.getAll()){
            links.add(file.getLocation());
        }
        return links;
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") long id){
        return service.getById(id).getLocation();
    }

    @PostMapping
    public String setById(@RequestParam("file") MultipartFile file){
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


    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable("id") long id) {
        service.deleteById(id);
        return service.getAll().toString();
    }
}

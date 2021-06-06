package com.max.springproject.controllers;

import com.max.springproject.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
public class FileController {
    private final FileService service;

    @Autowired
    public FileController(FileService service) {
        this.service = service;
    }

    @GetMapping("/files")
    public String getFiles(){
        return service.getAll().toString();
    }

    @GetMapping("/files/{id}")
    public String getById(@PathVariable("id") long id){
       return service.getById(id).toString();
    }

    @PostMapping("/files")
    public String getById(@RequestParam("file") MultipartFile file){
        com.max.springproject.models.File object = new com.max.springproject.models.File();
        object.setName(file.getOriginalFilename());
        File targetFile = new File("src/main/resources/files/" + file.getOriginalFilename());
        try(OutputStream outputStream = new FileOutputStream(targetFile)) {
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

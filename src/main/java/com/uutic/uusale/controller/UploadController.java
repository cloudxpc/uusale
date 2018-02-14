package com.uutic.uusale.controller;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uutic.uusale.exceptions.CustomException;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    @Value("${upload-folder}")
    private String uploadFolder;

    private String saveUploadedFile(MultipartFile file) throws Exception {
        if (file.isEmpty())
            throw new Exception("file is empty");

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadFolder, newFilename);
        Files.write(path, bytes);
        return newFilename;
    }

    @RequestMapping("")
    public String upload(@RequestParam("file") MultipartFile uploadFile) throws Exception {
        if (uploadFile.isEmpty())
            throw new CustomException("文件内容为空");

        String filename = saveUploadedFile(uploadFile);
        return new ObjectMapper().writeValueAsString(filename);
    }
}

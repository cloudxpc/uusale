package com.uutic.uusale.controller;

import com.uutic.uusale.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class ReportController {
    @Autowired
    private OrderService orderService;

    private void downloadFile(HttpServletResponse response, String path) throws IOException {
        File file = new File(path);
        BufferedOutputStream bufferedOutputStream = null;
        try {
            response.setContentLength((int) file.length());
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(file.getName(), "UTF-8") + "\"");
            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            bufferedOutputStream.write(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            bufferedOutputStream.flush();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
    }

    @GetMapping("/report/{from}/{to}")
    public synchronized void report(@PathVariable String from, @PathVariable String to, HttpServletResponse response) throws Exception {
        String path = orderService.generateOrderReport(from, to);
        downloadFile(response, path);
    }
}

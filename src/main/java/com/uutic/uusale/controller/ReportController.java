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

    private void downloadFile(HttpServletResponse response, byte[] data) throws IOException {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            response.setContentLength((int) data.length);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode("导出订单.xlsx", "UTF-8") + "\"");
            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            bufferedOutputStream.write(data);
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

    @GetMapping("/api/report/{from}/{to}")
    public synchronized void report(@PathVariable String from, @PathVariable String to, HttpServletResponse response) throws Exception {
        downloadFile(response, orderService.generateOrderReport(from, to));
    }

    @GetMapping("/api/report/{orderId}")
    public synchronized void report(@PathVariable String orderId, HttpServletResponse response) throws Exception {
        downloadFile(response, orderService.generateOrderReport(orderId));
    }
}

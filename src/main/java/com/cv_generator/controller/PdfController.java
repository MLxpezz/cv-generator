package com.cv_generator.controller;

import com.cv_generator.service.PdfGeneratorService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    private final PdfGeneratorService pdfGeneratorService;

    public PdfController(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public void generatePdf(@RequestBody Map<String, String> htmlRequest, HttpServletResponse response) {

        String html = htmlRequest.get("html");

        try {
            byte[] pdf = pdfGeneratorService.generatePdf(html);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=cv-template.pdf");
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(pdf);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

package com.deepak.assignment.controller;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PdfController {

    @Value("${pdf.file.prefix_pdf}")
    private String pdfFilePrefix;

    @Value("${pdf.file.directory_pdf}")
    private String pdfFileDirectory;

    @GetMapping("/generate-pdf")
    public ResponseEntity<?> generatePdfFile() {
        String filename = pdfFilePrefix + LocalDateTime.now() + ".pdf";
        String filepath = pdfFileDirectory + "/" + filename;

        try (Document document = new Document()) {
            PdfWriter.getInstance(document, new FileOutputStream(filepath));
            document.open();
            document.add(new Paragraph("Hello, PDF!"));
            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(headers, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("Failed to generate PDF file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

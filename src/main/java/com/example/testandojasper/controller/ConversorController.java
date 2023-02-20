package com.example.testandojasper.controller;

import com.example.testandojasper.service.ConversorService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ConversorController {

    private final ConversorService conversorService;

    private static ResponseEntity.BodyBuilder getDefaultResponseEntity() {
        return ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", "attachment; filename=export.pdf");
    }

    @PostMapping(value = "teste")
    public ResponseEntity<StreamingResponseBody> getCarai(HttpServletResponse response, @RequestBody String xml) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=export.pdf");

        byte[] outputStream = conversorService.generatePDFReport(response, xml);

        response.getOutputStream().write(outputStream);
        response.getOutputStream().flush();

        return getDefaultResponseEntity()
                .body(out -> conversorService.generatePDFReport(response, xml));

    }
}
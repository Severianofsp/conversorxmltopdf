package com.example.testandojasper.service;

import jakarta.servlet.http.HttpServletResponse;

public interface ConversorService {

    byte[] generatePDFReport(HttpServletResponse response, String xml);
}

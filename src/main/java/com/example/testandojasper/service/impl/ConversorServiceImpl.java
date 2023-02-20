package com.example.testandojasper.service.impl;

import com.example.testandojasper.exceptions.ConversionException;
import com.example.testandojasper.exceptions.ErrorResponse;
import com.example.testandojasper.service.ConversorService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConversorServiceImpl implements ConversorService {

    @Override
    public byte[] generatePDFReport(HttpServletResponse response, String xml) {
        try {
            Document doc = parseStringToDocument(xml);

            JasperPrint jasperPrint = configJasperAndFillReport(doc);

            return transformToPDF(jasperPrint);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(),e.getStackTrace());
            throw new ConversionException(error);
        }
    }


    private Document parseStringToDocument(String xml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    private JasperPrint configJasperAndFillReport(Document doc) throws JRException, FileNotFoundException {
        JRXmlDataSource dataSource = new JRXmlDataSource(doc, "nfeProc");
        Map<String, Object> parameters = new HashMap<>();

        JasperReport compileReport = JasperCompileManager
                .compileReport(new FileInputStream("nfe_danfe.jrxml"));

        return JasperFillManager.fillReport(compileReport, parameters, dataSource);
    }


    private byte[] transformToPDF(JasperPrint jasperPrint) throws JRException {
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}


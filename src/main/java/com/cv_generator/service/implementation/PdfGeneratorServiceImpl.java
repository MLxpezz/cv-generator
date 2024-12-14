package com.cv_generator.service.implementation;

import com.cv_generator.service.PdfGeneratorService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    public byte[] generatePdf(String html) {

        Document document = Jsoup.parse(html);
        document.outputSettings(new Document.OutputSettings().prettyPrint(true));

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            File file = new File("src/main/resources/static/fonts/Roboto-Regular.ttf");

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withW3cDocument(new W3CDom().fromJsoup(document), "/");
            builder.useFont(file, "Roboto");
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

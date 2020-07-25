package com.fanfiction.service;


import com.fanfiction.models.Chapter;
import com.fanfiction.models.Composition;
import com.fanfiction.repository.ChapterRepository;
import com.fanfiction.repository.CompositionRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ExportPdfService {

    private final BaseFont bf = BaseFont.createFont("arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    private final Font fontTitle = new Font(bf, 20, 1);
    private final Font fontContent = new Font(bf, 16, 1);
    private final Font fontTextItalics = new Font(bf, 14, 2);
    private final Font fontForChapterTitle = new Font(bf, 16, 1);

    @Autowired
    private CompositionRepository compositionRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    public ExportPdfService() throws IOException, DocumentException {
    }

    public void exportPdf(Long compositionId, HttpServletResponse response) throws IOException, DocumentException {
        Composition composition = compositionRepository.findById(compositionId).get();
        List<Chapter> chapters = chapterRepository.findAllByComposition(composition);

        Document document = new Document(PageSize.A4);
        PdfWriter instance = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        createTitleAndContext(document, composition, chapters);
        createChapters(instance, document, chapters);
        document.close();
    }

    private void createTitleAndContext(Document document, Composition composition, List<Chapter> chapters) throws DocumentException {
        Paragraph title = new Paragraph(composition.getTitle(), fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph(composition.getDescription(), fontTextItalics));
        document.add(Chunk.NEWLINE);

        Paragraph content = new Paragraph("Content", fontContent);
        content.setAlignment(Element.ALIGN_CENTER);
        document.add(content);

        for (int i = 0; i < chapters.size(); i++) {
            document.add(new Paragraph((i + 1) + ". " + chapters.get(i).getChaptername(), fontTextItalics));
        }
        document.add(Chunk.NEWLINE);
    }

    private void createChapters(PdfWriter writer, Document document, List<Chapter> chapters) throws IOException, DocumentException {
        for (int i = 0; i < chapters.size(); i++) {
            Paragraph chapterTitle = new Paragraph(chapters.get(i).getChaptername(), fontForChapterTitle);
            chapterTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(chapterTitle);

            if (chapters.get(i).getImgUrl() != null) {
                Image image = Image.getInstance(chapters.get(i).getImgUrl());
                image.scaleToFit(400, 400);
                image.setAlignment(Element.ALIGN_CENTER);
                document.add(image);
            }
            parseText(chapters.get(i), writer, document);
        }
    }

    private void parseText(Chapter chapter, PdfWriter writer, Document document) throws IOException {
        String htmlContent = "<h4 style=\"font-family: arialuni, arial; font-weight: normal; \" >"
                + chapter.getText() + "</h4>";
        InputStream is = new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8));
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register("arial.ttf");
        FontFactory.setFontImp(fontImp);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                is, null, StandardCharsets.UTF_8, fontImp);
    }
}

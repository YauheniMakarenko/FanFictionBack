package com.fanfiction.service;

import com.fanfiction.models.Composition;
import com.fanfiction.repository.ChapterRepository;
import com.fanfiction.repository.CompositionRepository;
import com.fanfiction.models.Chapter;
import com.fanfiction.payload.request.ChapterRequest;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private CompositionService compositionService;

    @Autowired
    public CompositionRepository compositionRepository;


    public Chapter saveChapter(ChapterRequest chapterRequest) throws IOException {
        if (chapterRequest.getChaptername() == null || chapterRequest.getChaptername().equals("")) {
            return null;
        }
        Chapter chapter = new Chapter();
        chapter.setId(chapterRequest.getId());
        chapter.setChaptername(chapterRequest.getChaptername());
        chapter.setText(chapterRequest.getText());

        Composition composition = compositionService.findCompositionById(chapterRequest.getCompositionId());
        composition.setPublicationDate(String.valueOf(new java.sql.Timestamp(new Date().getTime())).replaceAll("\\.\\d+", ""));
        compositionRepository.save(composition);

        chapter.setComposition(composition);
        chapter.setNumberChapter(chapterRequest.getNumberChapter());
        if (chapterRequest.getImgUrl() != null) {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dtvwmubpz",
                    "api_key", "973587197564797",
                    "api_secret", "nPzYrt2m8xuYvYf7sxCzkqZfB-8"));
            Map params = ObjectUtils.asMap(
                    "public_id", chapter.toString(),
                    "overwrite", true,
                    "notification_url", "https://api.cloudinary.com/v1_1/dtvwmubpz/image/upload",
                    "resource_type", "image"
            );
            chapter.setImgUrl(cloudinary.uploader().upload(chapterRequest.getImgUrl(), params).get("secure_url").toString());
        }
        chapterRepository.save(chapter);
        return chapter;
    }

    public List<Chapter> allChaptersByComposition(Long code) {
        return chapterRepository.findAllByComposition(compositionService.findCompositionById(code)).stream()
                .sorted((chapter1, chapter2) -> Integer.parseInt(String.valueOf(chapter1.getNumberChapter() - chapter2.getNumberChapter())))
                .collect(Collectors.toList());
    }

    public void deleteChapter(Chapter chapter) {
        chapterRepository.delete(chapter);
        replacingChapters(allChaptersByComposition(chapter.getComposition().getId()));
    }

    public void replacingChapters(List<Chapter> chapterList) {
        for (int i = 0; i < chapterList.size(); i++) {
            chapterList.get(i).setNumberChapter(i + 1);
        }
        chapterRepository.saveAll(chapterList);
    }
}

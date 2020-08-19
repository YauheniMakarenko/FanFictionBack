package com.fanfiction.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fanfiction.DTO.ChapterDTO;
import com.fanfiction.models.Chapter;
import com.fanfiction.models.Composition;
import com.fanfiction.repository.ChapterRepository;
import com.fanfiction.repository.CompositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public CompositionRepository compositionRepository;

    @Value("${spring.cloudinary.api_key}")
    private String api_key;
    @Value("${spring.cloudinary.api_secret}")
    private String api_secret;
    @Value("${spring.cloudinary.notification_url}")
    private String notification_url;


    public void saveChapter(ChapterDTO chapterDTO) throws IOException {
        Chapter chapter = new Chapter();
        chapter.setId(chapterDTO.getId());
        chapter.setChaptername(chapterDTO.getChaptername());
        chapter.setText(chapterDTO.getText());

        Composition composition = compositionRepository.findById(chapterDTO.getCompositionId()).get();
        composition.setPublicationDate(String.valueOf(new java.sql.Timestamp(new Date().getTime())).replaceAll("\\.\\d+", ""));
        compositionRepository.save(composition);

        chapter.setComposition(composition);
        chapter.setNumberChapter(chapterDTO.getNumberChapter());
        if (chapterDTO.getImgUrl() != null) {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dtvwmubpz",
                    "api_key", api_key,
                    "api_secret", api_secret));
            Map params = ObjectUtils.asMap(
                    "public_id", chapter.toString(),
                    "overwrite", true,
                    "notification_url", notification_url,
                    "resource_type", "image"
            );
            chapter.setImgUrl(cloudinary.uploader().upload(chapterDTO.getImgUrl(), params).get("secure_url").toString());
        }
        chapterRepository.save(chapter);
    }

    public List<Chapter> allChaptersByComposition(Long code) {
        return chapterRepository.findAllByCompositionId(code).stream()
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

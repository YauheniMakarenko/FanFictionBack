package com.fanfiction.controllers;

import com.fanfiction.models.Chapter;
import com.fanfiction.payload.request.ChapterRequest;
import com.fanfiction.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @PostMapping("/savechapter")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Chapter saveChapter(@Valid @RequestBody ChapterRequest chapterRequest) throws IOException {
        return chapterService.saveChapter(chapterRequest);
    }

    @GetMapping("allChapters/{code}")
    public List<Chapter> getChaptersByComposition(@PathVariable Long code){
        return chapterService.allChaptersByComposition(code);
    }

    @PostMapping("/deletechapter")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public void deleteChapter(@RequestBody Chapter chapter){
        chapterService.deleteChapter(chapter);
	}

	@PostMapping("/replacingchapters")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public void replacingChapters(@Valid @RequestBody List<Chapter> chapterList){
        chapterService.replacingChapters(chapterList);
	}
}

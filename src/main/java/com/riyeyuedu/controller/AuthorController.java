package com.riyeyuedu.controller;

import com.riyeyuedu.entity.ChapterEntity;
import com.riyeyuedu.entity.NovelEntity;
import com.riyeyuedu.entity.ResponseEntity;
import com.riyeyuedu.service.ChapterService;
import com.riyeyuedu.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class AuthorController {
    private NovelService novelService;

    private ChapterService chapterService;

    @Autowired
    public void setNovelService(NovelService novelService) {
        this.novelService = novelService;
    }

    @Autowired
    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @RequestMapping(value = "/author/createNovel", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity createNovel(@RequestBody NovelEntity novelEntity) {
        novelEntity.setAllow(0);
        novelEntity.setChapterNum(0);
        novelEntity.setClickNum(0);
        novelEntity.setClickNum(0);
        novelEntity.setCollectNum(0);
        novelEntity.setCreateTime(new Date().getTime());
        novelEntity.setRecommendNum(0);
        novelEntity.setWordNum(0);
        novelEntity.setScore(1.0);
        novelEntity.setState(-1);
        novelEntity.setCover("https://nealcaffrey.oss-cn-beijing.aliyuncs.com/default/default.png");

        novelService.addNovel(novelEntity);

        return new ResponseEntity(200, "success", novelEntity.getNid());
    }

    @RequestMapping(value = "/author/novel/{uid}")
    @CrossOrigin
    public ResponseEntity getNovel(@PathVariable("uid") int uid) {
        return new ResponseEntity(200, "success", novelService.getNovelByUid(uid));
    }

    @RequestMapping(value = "/author/chapterDraft/{nid}")
    @CrossOrigin
    public ResponseEntity getChaptersDraft(@PathVariable("nid") Long nid) {
        return new ResponseEntity(200, "success", chapterService.getChapterDraftInfo(nid));
    }

    @RequestMapping(value = "/author/chapterInfo/{nid}")
    @CrossOrigin
    public ResponseEntity getChapterInfo(@PathVariable("nid") Long nid) {
        return new ResponseEntity(200, "success", chapterService.getChapterInfo(nid));
    }

    @RequestMapping(value = "/author/createChapter", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity createChapter(@RequestBody ChapterEntity chapterEntity) {
        Long date = new Date().getTime();
        chapterEntity.setCreateTime(date);
        chapterEntity.setWordNum(chapterEntity.getContent().length());
        //System.out.println(chapterEntity.getWordNum());
        novelService.updateUpdateTime(date);

        //未写后台，上传的章节暂时默认为通过审核
        chapterEntity.setAllow(1);
        if (chapterEntity.getCid() != null) {
            chapterService.updateContent(chapterEntity);
            return new ResponseEntity(200, "success", true);
        } else {
            chapterService.addChapter(chapterEntity);
            novelService.addChapterNum(chapterEntity.getNid());
            return new ResponseEntity(200, "success", chapterEntity.getCid());
        }
    }

    @RequestMapping(value = "/author/createDraft", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity createDraft(@RequestBody ChapterEntity chapterEntity) {

        chapterEntity.setCreateTime(new Date().getTime());
        chapterEntity.setWordNum(chapterEntity.getDraft().length());
        chapterEntity.setAllow(0);
        chapterEntity.setIsDraft(1);

        if (chapterEntity.getCid() != null) {
            chapterService.updateDraft(chapterEntity);
            return new ResponseEntity(200, "success", true);
        } else {
            chapterService.addDraft(chapterEntity);
            return new ResponseEntity(200, "success", chapterEntity.getCid());
        }
    }

    @RequestMapping(value = "/author/getDraft/{cid}")
    @CrossOrigin
    public ResponseEntity getDraft(@PathVariable("cid") Long cid) {
        return new ResponseEntity(200, "success", chapterService.getDraftByCid(cid));
    }

    @RequestMapping(value = "/author/chapter/{cid}")
    @CrossOrigin
    public ResponseEntity getChapterInfoByCid(@PathVariable("cid") Long cid) {
        return new ResponseEntity(200, "success", chapterService.getChapterInfoByCid(cid));
    }

    @RequestMapping(value = "/author/deleteDraft/{cid}")
    @CrossOrigin
    public ResponseEntity deleteDraft(@PathVariable("cid") Long cid) {
        chapterService.deleteDraft(cid);

        return new ResponseEntity(200, "success", true);
    }

    @RequestMapping(value = "/author/deleteChapter/{cid}")
    @CrossOrigin
    public ResponseEntity deleteChapter(@PathVariable("cid") Long cid) {
        chapterService.deleteChapter(cid);
        return new ResponseEntity(200, "success", true);
    }
}

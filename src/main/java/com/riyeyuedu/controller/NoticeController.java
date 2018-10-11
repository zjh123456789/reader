package com.riyeyuedu.controller;

import com.riyeyuedu.entity.NoticeEntity;
import com.riyeyuedu.service.NoticeService;
import com.riyeyuedu.util.RichTextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author haojie tang
 * @date 2018/10/10 9:15
 */
@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping(value = "/noticeList")
    @CrossOrigin
    public ResponseEntity<?> getLatestNotice() {
        List<NoticeEntity> list = noticeService.getNoticeListL7();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/notice/{id}")
    @CrossOrigin
    public ResponseEntity<?> getNotice(@PathVariable int id) {
        NoticeEntity noticeEntity = noticeService.getNoticeById(id);
        noticeEntity.setContentStr(RichTextUtil.byteToStringWithBr(noticeEntity.getContent()));
        return ResponseEntity.ok(noticeEntity);
    }
}

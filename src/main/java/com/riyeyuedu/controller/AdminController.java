package com.riyeyuedu.controller;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.riyeyuedu.controller.Format.IndexChapterFormat;
import com.riyeyuedu.controller.Format.IndexNovelFormat;
import com.riyeyuedu.entity.MessageEntity;
import com.riyeyuedu.entity.NoticeEntity;
import com.riyeyuedu.entity.UserEntity;
import com.riyeyuedu.service.*;
import com.riyeyuedu.util.RichTextUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Neal Caffrey
 * 管理员
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private NovelService novelService;

    private ChapterService chapterService;

    private NoticeService noticeService;

    private PostService postService;

    private ReplyService replyService;

    private MessageService messageService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserEntity userEntity) {
        if (userService.getUser(userEntity) != null) {
            return ResponseEntity.ok(userService.getUser(userEntity));
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping(value = "/findByUsername")
    public ResponseEntity<?> getByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getReaderByName(username));
    }

    @GetMapping(value = "/novelList")
    public ResponseEntity<?> novelList(IndexNovelFormat indexNovelFormat,
                                       @RequestParam int pageNum,
                                       @RequestParam int pageSize) {
        if (indexNovelFormat.getUsername() != null && !"".equals(indexNovelFormat.getUsername())) {
            if (userService.getReaderByName(indexNovelFormat.getUsername()) == null) {
                return ResponseEntity.ok(null);
            } else {
                indexNovelFormat.setUid(userService.getReaderByName(indexNovelFormat.getUsername()).getUid());
            }
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = novelService.getNovelList(indexNovelFormat);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResponseEntity.ok(pageInfo);
    }

    @GetMapping(value = "/auditNovel")
    public ResponseEntity<?> auditNovel(@RequestParam Long nid,
                                        @RequestParam Integer allow) {
        return ResponseEntity.ok(novelService.updateNovelAllow(nid, allow));
    }

    @GetMapping(value = "/chapterList")
    public ResponseEntity<?> chapterList(IndexChapterFormat indexChapterFormat,
                                         @RequestParam int pageNum,
                                         @RequestParam int pageSize) {
        if (indexChapterFormat.getUsername() != null && !"".equals(indexChapterFormat.getUsername())) {
            if (userService.getReaderByName(indexChapterFormat.getUsername()) == null) {
                return ResponseEntity.ok(null);
            } else {
                indexChapterFormat.setUid(userService.getReaderByName(indexChapterFormat.getUsername()).getUid());
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = chapterService.getWillAuditChapter(indexChapterFormat);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResponseEntity.ok(pageInfo);
    }

    @GetMapping(value = "/auditChapter")
    public ResponseEntity<?> auditChapter(@RequestParam Long cid,
                                          @RequestParam Integer allow) {
        return ResponseEntity.ok(chapterService.updateChapterAllow(cid, allow));
    }

    @PostMapping(value = "/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody MessageEntity messageEntity) {
        messageEntity.setTime(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        return ResponseEntity.ok(messageService.addMessage(messageEntity));
    }

    @GetMapping(value = "/userList")
    public ResponseEntity<?> userList(UserEntity userEntity,
                                      @RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize) {
        if (userEntity.getUsername() != null && !"".equals(userEntity.getUsername())) {
            if (userService.getReaderByName(userEntity.getUsername()) == null) {
                return ResponseEntity.ok(null);
            } else {
                userEntity.setUid(userService.getReaderByName(userEntity.getUsername()).getUid());
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<UserEntity> list = userService.getAllUser(userEntity);
        PageInfo<UserEntity> pageInfo = new PageInfo<>(list);
        return ResponseEntity.ok(pageInfo);
    }

    @GetMapping(value = "/noticeList")
    public ResponseEntity<?> noticeList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam Integer order) {
        PageHelper.startPage(pageNum, pageSize);
        List<NoticeEntity> list = noticeService.getNoticeList();

        for (NoticeEntity item : list) {
            item.setContentStr(RichTextUtil.byteToStringWithBr(item.getContent()));
        }

        PageInfo<NoticeEntity> pageInfo = new PageInfo<>(list);
        return ResponseEntity.ok(pageInfo);
    }

    @PostMapping(value = "/addNotice")
    public ResponseEntity<?> addNotice(@RequestBody NoticeEntity noticeEntity) {
        noticeEntity.setCreateTime(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        noticeEntity.setUpdateTime(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        noticeEntity.setContent(RichTextUtil.stringToByte(noticeEntity.getContentStr()));
        return ResponseEntity.ok(noticeService.addNotice(noticeEntity));
    }

    @PostMapping(value = "/updateNotice")
    public ResponseEntity<?> updateNotice(@RequestBody NoticeEntity noticeEntity) {
        noticeEntity.setUpdateTime(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        noticeEntity.setContent(RichTextUtil.stringToByte(noticeEntity.getContentStr()));
        return ResponseEntity.ok(noticeService.updateNotice(noticeEntity));
    }

    @DeleteMapping(value = "/deleteNotice")
    public ResponseEntity<?> deleteNotice(@RequestParam int id) {
        return ResponseEntity.ok(noticeService.deleteNotice(id));
    }
}

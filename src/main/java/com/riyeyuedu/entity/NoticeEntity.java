package com.riyeyuedu.entity;

import lombok.*;


/**
 * @author haojie tang
 * @date 2018/10/4 9:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NoticeEntity {
    private int id;

    private String title;
    
    private byte[] content;

    private String contentStr;

    private Long createTime;

    private Long updateTime;

}
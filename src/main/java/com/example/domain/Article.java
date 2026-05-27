package com.example.domain;

import lombok.*;

import java.util.List;

/**
 * 記事情報を表すドメイン.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Article {
    /**
     * 記事ID
     */
    private Integer id;
    /**
     * 投稿者名
     */
    private String name;
    /**
     * 記事本文
     */
    private String content;
    /**
     * 記事に紐づくコメントのリスト
     */
    private List<Comment> commentList;
}
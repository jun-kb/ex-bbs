package com.example.form;

import lombok.*;

/**
 * 記事投稿時に画面から送られるリクエストデータを格納するフォーム.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ArticleForm {
    /**
     * 投稿者名
     */
    private String name;
    /**
     * 記事本文
     */
    private String content;
}
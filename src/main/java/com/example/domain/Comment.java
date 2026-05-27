package com.example.domain;

import lombok.*;

/**
 * 記事に対するコメント情報を表すドメイン.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Comment {
    /**
     * コメントID
     */
    private Integer id;
    /**
     * 投稿者名
     */
    private String name;
    /**
     * コメント内容
     */
    private String content;
    /**
     * 紐づく記事のID
     */
    private Integer articleId;
}
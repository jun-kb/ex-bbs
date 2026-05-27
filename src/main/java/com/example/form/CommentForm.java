package com.example.form;

import lombok.*;

/**
 * コメント投稿時に画面から送られるリクエストデータを格納するフォーム.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CommentForm {
    /**
     * 紐づく記事のID
     */
    private Integer articleId;
    /**
     * 投稿者名
     */
    private String name;
    /**
     * コメント内容
     */
    private String content;
}
package com.example.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "投稿者名を入力してください")
    @Size(max = 50, message = "投稿者名は50字以内で入力してください")
    private String articleName;
    /**
     * 記事本文
     */
    @NotBlank(message = "投稿内容を入力してください")
    private String articleContent;
}
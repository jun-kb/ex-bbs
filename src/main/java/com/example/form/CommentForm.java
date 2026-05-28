package com.example.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "名前を入力してください")
    @Size(max = 10, message = "名前は50字以内で入力してください")
    private String commentName;
    /**
     * コメント内容
     */
    @NotBlank(message = "コメントを入力してください")
    private String commentContent;
}
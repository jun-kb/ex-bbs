package com.example.controller;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 掲示板の記事およびコメントの投稿・削除・表示を制御するコントローラー.
 */
@Controller
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    /**
     * 掲示板のトップ画面を表示し、記事とそれに紐づくコメントの一覧を取得する.
     *
     * @param model       画面にデータを渡すためのModelオブジェクト
     * @param articleForm 記事投稿用のフォームオブジェクト
     * @param commentForm コメント投稿用のフォームオブジェクト
     * @return 遷移先のトップ画面テンプレート名
     */
    @GetMapping("")
    public String index(Model model, ArticleForm articleForm, CommentForm commentForm) {
        List<Article> articleList = articleRepository.fastFindAll();

        model.addAttribute("articles", articleList);

        return "top-page";
    }

    /**
     * 記事を投稿する.
     *
     * @param form        バリデーション済みの記事投稿用フォームオブジェクト
     * @param result      バリデーション結果のBindingResultオブジェクト
     * @param model       画面にデータを渡すためのModelオブジェクト
     * @param commentForm コメント投稿用のフォームオブジェクト
     * @return エラーがある場合はトップ画面、正常終了時はトップ画面へのリダイレクトパス
     */
    @PostMapping("/insert")
    public String insertArticle(@Validated ArticleForm form, BindingResult result, Model model, CommentForm commentForm) {
        if (result.hasErrors()) {
            return index(model, form, commentForm);
        }

        Article article = new Article();
        article.setName(form.getArticleName());
        article.setContent(form.getArticleContent());

        articleRepository.insert(article);
        return "redirect:/bbs";
    }

    /**
     * コメントを投稿する.
     *
     * @param form        バリデーション済みのコメント投稿用フォームオブジェクト
     * @param result      バリデーション結果のBindingResultオブジェクト
     * @param model       画面にデータを渡すためのModelオブジェクト
     * @param articleForm 記事投稿用のフォームオブジェクト
     * @return エラーがある場合はトップ画面、正常終了時はトップ画面へのリダイレクトパス
     */
    @PostMapping("/insert-comment")
    public String insertComment(@Validated CommentForm form, BindingResult result, Model model, ArticleForm articleForm) {
        if (result.hasErrors()) {
            return index(model, articleForm, form);
        }

        Comment comment = new Comment();
        comment.setName(form.getCommentName());
        comment.setContent(form.getCommentContent());
        comment.setArticleId(form.getArticleId());
        commentRepository.insert(comment);
        return "redirect:/bbs";
    }

    /**
     * 記事を削除する.
     *
     * @param articleId 削除対象の記事ID
     * @return トップ画面へのリダイレクトパス
     */
    @PostMapping("/delete")
    @Transactional
    public String deleteArticle(Integer articleId) {
        articleRepository.deleteById(articleId);

        return "redirect:/bbs";
    }
}
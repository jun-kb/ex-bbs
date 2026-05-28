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

@Controller
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    /**
     * 記事とそれに紐づくコメントの一覧画面を表示する.
     */
    @GetMapping("")
    public String index(Model model, ArticleForm articleForm, CommentForm commentForm) {
        List<Article> articleList = articleRepository.findAll();

        for (Article article : articleList) {
            List<Comment> commentList = commentRepository.findByArticleId(article.getId());
            article.setCommentList(commentList);
        }

        model.addAttribute("articles", articleList);

        return "top-page";
    }

    /**
     * 記事を投稿する.
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
     */
    @PostMapping("/delete")
    @Transactional
    public String deleteArticle(Integer articleId) {
        commentRepository.deleteByArticleId(articleId);
        articleRepository.deleteById(articleId);

        return "redirect:/bbs";
    }
}
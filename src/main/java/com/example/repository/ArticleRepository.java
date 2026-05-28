package com.example.repository;

import com.example.domain.Article;
import com.example.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 記事情報のデータベース操作を行うリポジトリ.
 */
@Repository
@RequiredArgsConstructor
public class ArticleRepository {

    private final NamedParameterJdbcTemplate template;

    private static final RowMapper<Article> ARTICLE_ROW_MAPPER = new BeanPropertyRowMapper<>(Article.class);
    private static final RowMapper<Article> ARTICLE_ROW_MAPPER_FAST = (rs, i) -> {
        Article article = Article.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .content(rs.getString("content"))
                .build();

        if (Objects.isNull(rs.getObject("com_id"))) {
            return article;
        }

        List<Comment> commentList = new LinkedList<>();
        commentList.add(
                Comment.builder()
                        .id(rs.getInt("com_id"))
                        .name(rs.getString("com_name"))
                        .content(rs.getString("com_content"))
                        .articleId(rs.getInt("id"))
                        .build()
        );
        article.setCommentList(commentList);
        return article;
    };

    /**
     * 記事情報を全件取得する.
     *
     * @return 取得した記事情報のリスト
     */
    public List<Article> findAll() {
        String sql = """
                SELECT id, name, content 
                FROM articles 
                ORDER BY id DESC;
                """;
        return template.query(sql, ARTICLE_ROW_MAPPER);
    }

    public List<Article> fastFindAll() {
        String sql = """
                SELECT a.id, a.name, a.content,
                c.id AS com_id,
                c.name AS com_name,
                c.content AS com_content,
                c.article_id
                FROM articles AS a
                LEFT OUTER JOIN comments AS c
                ON a.id = c.article_id
                ORDER BY a.id DESC, c.id DESC;
                """;
        List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER_FAST);
        Map<Integer, Article> articleMap = new LinkedHashMap<>();
        for (Article article : articleList) {
            int currentId = article.getId();
            if (!articleMap.containsKey(currentId)) {
                articleMap.put(currentId, article);
            } else {
                articleMap.get(currentId).getCommentList().add(article.getCommentList().getFirst());
            }
        }
        return articleMap.values().stream().toList();
    }

    /**
     * 記事を投稿する.
     */
    public void insert(Article article) {
        String sql = "INSERT INTO articles(name, content) VALUES(:name, :content);";
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);
        template.update(sql, param);
    }

    /**
     * 指定されたIDの記事を削除する.
     */
    public void deleteById(Integer id) {
        String sql = "DELETE FROM articles WHERE id = :id;";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(sql, param);
    }
}
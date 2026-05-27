package com.example.repository;

import com.example.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 記事情報のデータベース操作を行うリポジトリ.
 * (articlesテーブルのみにアクセスします)
 */
@Repository
@RequiredArgsConstructor
public class ArticleRepository {

    private final NamedParameterJdbcTemplate template;

    private static final RowMapper<Article> ARTICLE_ROW_MAPPER = new BeanPropertyRowMapper<>(Article.class);

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

    /**
     * 記事を投稿する.
     */
    public void insert(Article article) {
        String sql = "INSERT INTO articles(name, content) VALUES(:name, :content);";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", article.getName())
                .addValue("content", article.getContent());
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
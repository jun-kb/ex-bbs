package com.example.repository;

import com.example.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * コメント情報のデータベース操作を行うリポジトリ.
 */
@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final NamedParameterJdbcTemplate template;

    private static final RowMapper<Comment> ROW_MAPPER = new BeanPropertyRowMapper<>(Comment.class);

    /**
     * 指定した記事IDに紐づくコメントを取得する.
     *
     * @param articleId 記事ID
     * @return 該当記事のコメントリスト
     */
    public List<Comment> findByArticleId(Integer articleId) {
        String sql = """
                SELECT id, name, content, article_id 
                FROM comments 
                WHERE article_id = :articleId 
                ORDER BY id DESC;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
        return template.query(sql, param, ROW_MAPPER);
    }

    /**
     * コメントを投稿する.
     */
    public void insert(Comment comment) {
        String sql = "INSERT INTO comments(name, content, article_id) VALUES(:name, :content, :articleId);";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", comment.getName())
                .addValue("content", comment.getContent())
                .addValue("articleId", comment.getArticleId());
        template.update(sql, param);
    }

    /**
     * 記事に紐づくコメントをすべて削除する.
     */
    public void deleteByArticleId(Integer articleId) {
        String sql = "DELETE FROM comments WHERE article_id = :articleId;";
        SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
        template.update(sql, param);
    }
}
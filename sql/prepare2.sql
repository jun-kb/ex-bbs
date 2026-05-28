DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS articles;
CREATE TABLE articles(
  id serial PRIMARY KEY, -- 投稿ID
  name text NOT NULL,    -- 名前
  content text NOT NULL  -- 記事内容
);
CREATE TABLE comments(
  id serial PRIMARY KEY, -- コメントID
  name text NOT NULL,    -- 名前
  content text NOT NULL, -- コメント内容
  article_id integer REFERENCES articles (id) ON DELETE CASCADE  --投稿ID
);
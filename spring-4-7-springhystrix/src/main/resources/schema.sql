DROP TABLE IF EXISTS book_comment;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS genre;

-- Авторы книг
CREATE TABLE author
(
    id        bigserial PRIMARY KEY
   ,brief     VARCHAR(25)  NOT NULL   -- Сокращение
   ,last_name  VARCHAR(255) NOT NULL  -- Фамилия
   ,first_name VARCHAR(255) NOT NULL  -- Имя

   ,UNIQUE (brief)
);

-- Жанры книг
CREATE TABLE genre
(
    id    bigserial PRIMARY KEY
   ,brief VARCHAR(25)  NOT NULL      -- Сокращение
   ,name  VARCHAR(255) NOT NULL      -- Наименование

   ,UNIQUE (brief)
);

-- Книги
CREATE TABLE book
(
    id       bigserial PRIMARY KEY
   ,brief    VARCHAR(25)   NOT NULL   -- Сокращение
   ,title    VARCHAR(255)  NOT NULL   -- Заголовок
   ,text     VARCHAR(8000) NOT NULL   -- Текст
   ,author_id BIGINT        NOT NULL  -- Идентификатор автора книги
   ,genre_id  BIGINT        NOT NULL  -- Идентификатор жанра книги

   ,FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE
   ,FOREIGN KEY (genre_id) REFERENCES genre (id) ON DELETE CASCADE

   ,UNIQUE (brief)
);

-- Комментарии к книге
CREATE TABLE book_comment
(
    id      bigserial PRIMARY KEY
   ,book_id BIGINT        NOT NULL  -- Идентификатор книги
   ,comment VARCHAR(1000) NOT NULL  -- Комментарий

   ,FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE
);

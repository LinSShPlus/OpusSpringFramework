-- Авторы книг
DROP TABLE IF EXISTS Author;
CREATE TABLE Author
(
    id        bigserial PRIMARY KEY
   ,brief     VARCHAR(25)  NOT NULL  -- Сокращение
   ,lastName  VARCHAR(255) NOT NULL  -- Фамилия
   ,firstName VARCHAR(255) NOT NULL  -- Имя

   ,UNIQUE (brief)
);

-- Жанры книг
DROP TABLE IF EXISTS Genre;
CREATE TABLE Genre
(
    id    bigserial PRIMARY KEY
   ,brief VARCHAR(25)  NOT NULL      -- Сокращение
   ,name  VARCHAR(255) NOT NULL      -- Наименование

   ,UNIQUE (brief)
);

-- Книги
DROP TABLE IF EXISTS Book;
CREATE TABLE Book
(
    id       bigserial PRIMARY KEY
   ,brief    VARCHAR(25)   NOT NULL  -- Сокращение
   ,title    VARCHAR(255)  NOT NULL  -- Заголовок
   ,text     VARCHAR(8000) NOT NULL  -- Текст
   ,authorId BIGINT        NOT NULL  -- Идентификатор автора книги
   ,genreId  BIGINT        NOT NULL  -- Идентификатор жанра книги

   ,FOREIGN KEY (authorId) REFERENCES Author (id) ON DELETE CASCADE
   ,FOREIGN KEY (genreId) REFERENCES Genre (id) ON DELETE CASCADE

   ,UNIQUE (brief)
);

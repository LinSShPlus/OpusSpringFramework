-- Еда
DROP TABLE IF EXISTS food;
CREATE TABLE food
(
    id        bigserial PRIMARY KEY
   ,brief     VARCHAR(25)  NOT NULL   -- Сокращение
   ,name      VARCHAR(255) NOT NULL  -- Наименование

   ,UNIQUE (brief)
);

-- Соусы
DROP TABLE IF EXISTS sauce;
CREATE TABLE sauce
(
    id    bigserial PRIMARY KEY
   ,brief VARCHAR(25)  NOT NULL      -- Сокращение
   ,name  VARCHAR(255) NOT NULL      -- Наименование

   ,UNIQUE (brief)
);

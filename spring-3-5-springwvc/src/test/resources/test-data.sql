insert into author (brief, last_name, first_name)
values ('Ivanov I.', 'Ivanov', 'Ivan'),
       ('Petrov V.', 'Petrov', 'Vasya');

insert into genre (brief, name)
values ('Programming', 'Programming'),
       ('Detective', 'Detective'),
       ('Fantasy', 'Fantasy'),
       ('Business & Finance', 'Business & Finance'),
       ('Politics', 'Politics'),
       ('Biography', 'Biography'),
       ('Dictionary', 'Dictionary');

insert into book (brief, title, text, author_id, genre_id)
select 'Java_Begin', 'Java for Beginners', 'Text of Java for Beginners', a.id, g.id
  from author a, genre g
 where a.brief = 'Ivanov I.'
   and g.brief = 'Programming'
union all
select 'Java_Pro', 'Java for Professional', 'Text of Java for Professional', a.id, g.id
  from author a, genre g
 where a.brief = 'Petrov V.'
   and g.brief = 'Programming';

insert into book_comment (book_id, comment)
select b.id, 'Very good book for beginners'
  from Book b
 where b.brief = 'Java_Begin'
union all
select b.id, 'The plot of the book is incredibly interesting (exciting/ intricate/ rather simple, something special)'
  from Book b
 where b.brief = 'Java_Begin'
union all
select b.id, 'On the whole the book is good. It is worth reading.'
  from Book b
 where b.brief = 'Java_Pro';

insert into "user" (login, password, enabled)
select 'admin', 'ADMIN', '$2a$10$XbjcYrj.Qz3Qm7DgWw4G0evjM29v4rSXjxfx1KtU62joHMTGeyfUu', true
union all
select 'user', 'USER', '$2a$10$XbjcYrj.Qz3Qm7DgWw4G0evjM29v4rSXjxfx1KtU62joHMTGeyfUu', true
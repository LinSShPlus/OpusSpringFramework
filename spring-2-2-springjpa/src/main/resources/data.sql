insert into Author (brief, lastName, firstName)
values ('Ivanov I.', 'Ivanov', 'Ivan'),
       ('Petrov V.', 'Petrov', 'Vasya');

insert into Genre (brief, name)
values ('Programming', 'Programming'),
       ('Detective', 'Detective'),
       ('Fantasy', 'Fantasy'),
       ('Business & Finance', 'Business & Finance'),
       ('Politics', 'Politics'),
       ('Biography', 'Biography'),
       ('Dictionary', 'Dictionary');

insert into Book (brief, title, text, authorId, genreId)
select 'Java_Begin', 'Java for Beginners', 'Text of Java for Beginners', a.id, g.id
  from Author a, Genre g
 where a.brief = 'Ivanov I.'
   and g.brief = 'Programming'
union all
select 'Java_Pro', 'Java for Professional', 'Text of Java for Professional', a.id, g.id
  from Author a, Genre g
 where a.brief = 'Petrov V.'
   and g.brief = 'Programming';
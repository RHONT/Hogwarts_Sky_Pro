-- вопрос, как сделать в postgres регистро-нечувствительный текст?


alter table student
    add constraint age_constraint check ( age > 16 );

alter table student
    alter column age set default 20;

alter table student
    alter column name set not null;

alter table student
    add unique (name);

alter table faculty
    add constraint color_name_unique unique (color, name);


alter table student
    drop constraint age_constraint;



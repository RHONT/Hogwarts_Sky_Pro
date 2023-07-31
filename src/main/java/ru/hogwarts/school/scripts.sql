select s.*
from student as s
where s.age between 10 and 30

select s.name
from student as s

select s.*
from student as s
where s.name like ('%р%')

select s.*
from student as s
where s.age<s.id

select s.*
from student as s
order by s.age
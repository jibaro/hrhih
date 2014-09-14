select * from T_District order by id desc 
select * from T_city where proid=35
select * from T_Province


alter table T_Province add seqnum int(8) not null default 0;


UPDATE T_Province SET pingyin='beijinshi', szm='bjs',seqnum=2 where ProID=2;

select seqnum from T_city order by seqnum desc;


CREATE TABLE `testuser` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(80) NOT NULL DEFAULT '',
  `age` int(4) NOT NULL,
  `email` varchar(64) ,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

select * from testuser;

show tables;


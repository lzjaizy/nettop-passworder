INSERT INTO `user` (`userId`,`username`,`name`,`password`,`salt`,`state`)
VALUES ('1', 'admin', '管理员', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f', 0);

INSERT INTO `syspermission` (`permissionId`,`available`,`permissionname`,`parentid`,`parentids`,`permission`,`resourcetype`,`url`)
VALUES (1,0,'用户管理',0,'0/','user:view','menu','user/userList');
INSERT INTO `syspermission` (`permissionId`,`available`,`permissionname`,`parentid`,`parentids`,`permission`,`resourcetype`,`url`)
VALUES (2,0,'用户添加',1,'0/1','user:add','button','user/userAdd');
INSERT INTO `syspermission` (`permissionId`,`available`,`permissionname`,`parentid`,`parentids`,`permission`,`resourcetype`,`url`)
VALUES (3,0,'用户删除',1,'0/1','user:del','button','user/userDel');

INSERT INTO `sysrole` (`roleid`,`available`,`description`,`role`) VALUES (1,0,'管理员','admin');
INSERT INTO `sysrole` (`roleid`,`available`,`description`,`role`) VALUES (2,0,'VIP会员','vip');
INSERT INTO `sysrole` (`roleid`,`available`,`description`,`role`) VALUES (3,1,'test','test');

INSERT INTO `sysrolepermission` (`permissionid`,`roleid`) VALUES (1,1);
INSERT INTO `sysrolepermission` (`permissionid`,`roleid`) VALUES (2,1);
INSERT INTO `sysrolepermission` (`permissionid`,`roleid`) VALUES (3,2);

INSERT INTO `sysuserrole` (`roleid`,`userId`) VALUES (1,1);
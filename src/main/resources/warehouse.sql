CREATE DATABASE  IF NOT EXISTS `warehouse` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `warehouse`;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1234567890','Kylin','1234567890','12345678911'),('admin_','admin','000000',NULL),('kylin777','Kylin','aaaaaaaaa','12345678911'),('kylinseven','Kylin','aaaaaaaaa','12345678911'),('kylinseven777','Kylin','aaaaaaaaa','12345678911'),('lalalalalalal','web实验报告好多图','lalalalalalal','15753162776'),('liabaiooi','李八','yuu787897','12345678918'),('ljxljxljx','lxj','ljxljxljx','15753162776'),('webwebweb','web实验报告好多图','123456798','12345678911'),('zhangsan','weq','12345678','13484920667'),('zhangsanfeng','张三','12345678','12345678901');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;




-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES ('1','手机','数码',9999,'images/shuma_1.jpg',0,'智能手机拍照游戏官方正品','货架A',NULL),('10','电脑','数码',4399,'images/shuma_2.jpg',17,'DELL英特尔酷睿便携轻薄本','货架A',2),('11','相机','数码',12499,'images/shuma_3.jpg',16,'索尼全画幅微单相机','货架A',2),('12','充电器','数码',129,'images/shuma_4.jpg',16,' 小米充电宝2万毫安大容量','货架A',2),('13','键盘','数码',59,'images/shuma_5.jpg',7,'静音无声键盘机械','货架A',2),('15','鼠标','数码',759,'images/shuma_6.jpg',13,'高端电竞游戏宏鼠标送男友','货架A',2),('16','耳机','数码',39,'images/shuma_7.jpg',15,'EDIFIER/漫步者半入耳式','货架A',2),('17','显示器','数码',468,'images/shuma_8.jpg',5,' 27英寸曲面显示器高清','货架A',2),('18','硬盘','数码',100,NULL,10,'硬盘','货架A',22),('2','棉花糖','食品',27,'images/shipin_1.jpg',13,'【龚俊同款】徐福记棉花糖','货架C',2),('3','棒棒糖','食品',22,'images/shipin_2.jpg',13,'徐福记熊博士无糖棒棒糖','货架C',2),('4','口香糖','食品',50,'images/shipin_3.jpg',11,'炫迈混合口味薄荷口香糖','货架C',2),('5','麦芽糖','食品',10,'images/shipin_4.jpg',12,' 展艺麦芽糖棒棒糖','货架C',2),('6','葡萄糖','食品',67,'images/shipin_5.jpg',9,'吉天瑞葡萄糖口服液','货架C',2),('7','外套','衣服',119,'images/yifu_1.jpg',8,'森马外套冰凉降温防晒服','货架B',2),('8','裤子','衣服',139,'images/yifu_2.jpg',7,'唐狮2022新款牛仔裤','货架B',2),('9','上衣','衣服',69,'images/yifu_3.jpg',6,'衬衫短袖女设计感小众','货架B',2),('good1','ewr','食品',101,NULL,101,'阿八八八','书包',0);
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('29aa6f2e27cd4e5eb7cd6c6161189d83','1','1234567890',1,NULL,'111','111','15753162776','2022-06-24',1,1),('2e1cc77b90fa4b2ab0cfa1dbef4cc055','15','1234567890',0,NULL,'哈尔滨工业大学','ppp','12345678911','2022-06-30',0,1),('3219f0b7193a462dbe947a8675da84ca','1','1234567890',1,NULL,'123','12','12345678911','2022-06-30',10,1),('5e998fed9ca340769f7c229bedbdd866','1','zhangsan',1,NULL,'wqerrew','qwe','12345678916','2022-07-28',5,5),('61f1df49d086426596239769af2c7c37','1','1234567890',0,NULL,'阿八八八八八','课设好麻烦','12345678911','2022-06-24',0,1),('66faa8f17bb84890b34b0ca215ef8b23','1','1234567890',1,NULL,'111','111','15753162776','2022-06-24',2,1),('7ca301b70d294bd49864dffd0e2f083d','1','1234567890',0,NULL,'llllll','llllll','12345678911','2022-06-24',0,1),('872c5939aaa14707a458a85b3bf9def2','1','1234567890',1,NULL,'aaaaa','abab','12345678911','2022-06-24',3,1),('a4271278668242eabf05de369994899c','1','1234567890',0,NULL,'nnnn','bbb','15753162776','2022-06-24',0,1),('a7e95548700f4e548039348292e76470','1','1234567890',0,NULL,'mmmm','mmmm','12345678911','2022-06-24',0,1),('b805117073c24d5d80e99ed97bfb237b','1','1234567890',0,NULL,'hahahahah','hahahahah','15753162776','2022-06-24',0,1),('c459e6a666fc4296bc4926fb26f4f8ff','2','webwebweb',0,NULL,'哈尔滨工业大学','zhangsan','12345678911','2022-06-30',0,2),('dd64b548cfb9436ca085e2b263d664b8','1','1234567890',0,NULL,'aaa','aaaa','12345678911','2022-06-24',0,1),('dd8c07c1ebce4d348798492ce0ea5d06','1','1234567890',0,NULL,'不知道在哪','qwq','12345678911','2022-06-29',0,1),('f6a16ea8560f457ea1e07105ab1205b3','13','ljxljxljx',0,NULL,'哈尔滨工业大学威海','ljx','12345678911','2022-07-01',0,8);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Dumping data for table `add_to_warehouse`
--

LOCK TABLES `add_to_warehouse` WRITE;
/*!40000 ALTER TABLE `add_to_warehouse` DISABLE KEYS */;
INSERT INTO `add_to_warehouse` VALUES ('1001b655ebbd4fec9c28d983119c4944','1234567890','1','2022-06-22',100),('27008872c61e4ed4b88990fbd9384895','zhangsan','15','2022-06-29',1),('29cb232293d844c68a16ebfa28bddd97','admin_','good1','2022-07-01',101),('61dc9690ff1f4b9893274d4e0f178989','1234567890','1','2022-06-22',100),('74ea172d6bcd4097acea9bd97fc6fabf','1234567890','1','2022-06-22',100),('81e0e9f722184fb7b3e5afe2f4248da9','admin_','18','2022-06-30',10),('8f076ad0d73a4668a2c00018b39067b8','zhangsan','13','2022-06-27',1),('ade7f6538c174103baf18efa7b3c7948','1234567890','1','2022-06-22',100),('c306e7499aa24fdc81b30e0a2ef06965','1234567890','1','2022-06-22',100),('ca75bafdc5634ef38a5270db1a916f72','admin_','16','2022-06-30',10),('d1368e7408604281a3d9c1375c3239b6','1234567890','1','2022-06-22',7);
/*!40000 ALTER TABLE `add_to_warehouse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;



--
-- Dumping data for table `ex_warehouse`
--

LOCK TABLES `ex_warehouse` WRITE;
/*!40000 ALTER TABLE `ex_warehouse` DISABLE KEYS */;
INSERT INTO `ex_warehouse` VALUES ('364672a56bea404a99fc7aca46650913','dd8c07c1ebce4d348798492ce0ea5d06','1234567890'),('45098a3ee53149069ba3fe44624e4fe9','f6a16ea8560f457ea1e07105ab1205b3','ljxljxljx'),('5006a61aca2b4e83b76cb4328a3cd568','3219f0b7193a462dbe947a8675da84ca','1234567890'),('547d011587ee46f787007054d04645e9','c459e6a666fc4296bc4926fb26f4f8ff','webwebweb'),('912d77508a804951a911d2aa7177163b','5e998fed9ca340769f7c229bedbdd866','zhangsan'),('aab0209629f64116a0cff26b4511bb13','2e1cc77b90fa4b2ab0cfa1dbef4cc055','1234567890');
/*!40000 ALTER TABLE `ex_warehouse` ENABLE KEYS */;
UNLOCK TABLES;


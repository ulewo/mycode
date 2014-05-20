drop table vsgame;
drop table vsplayer;
drop table vsrival;
drop table vsround;
drop table vsuser;

drop table friendgroup;
drop table message;

alter table attached_file rename to ulewo_attachment;
alter table ulewo_attachment change id attachment_id int(11); 
alter table ulewo_attachment change articleid topic_id int(11) comment '文章ID';
alter table ulewo_attachment modify  gid int(11) comment '群组ID';
alter table ulewo_attachment change  filename file_name varchar(200) comment '文件名';
alter table ulewo_attachment change  fileurl file_url varchar(255) comment '文件地址';
alter table ulewo_attachment change  filetype file_type varchar(1) comment '文件类型';
alter table ulewo_attachment change  mark download_mark int(11) comment '下载所需积分';
alter table ulewo_attachment change  dcount download_count int(11) comment '下载次数';

alter table attached_user rename to ulewo_attachment_download;
alter table ulewo_attachment_download  change attachedid attached_id int(11) comment '附件ID';
alter table ulewo_attachment_download change userid userid int(11) comment '下载用户id';

alter table talk rename to ulewo_blast;
alter table ulewo_blast change id blast_id int(11) comment '吐槽ID';
alter table ulewo_blast change userid userid int(11) comment '用户ID';
alter table ulewo_blast drop column username;
alter table ulewo_blast drop column usericon;
alter table ulewo_blast change imgurl image_url varchar(255) comment '图片路径';
alter table ulewo_blast change content content text comment '内容';
alter table ulewo_blast change createtime create_time datetime comment '创建时间';
alter table ulewo_blast change sourcefrom source_from varchar(1) comment '来源 A:安卓 P:PC';

alter table retalk rename to ulewo_blast_comment;
alter table ulewo_blast_comment change id id int(11) comment '吐槽评论ID';
alter table ulewo_blast_comment change talkid blast_id int(11) comment '吐槽ID';
alter table ulewo_blast_comment change content content varchar(1000) comment '评论内容';
alter table ulewo_blast_comment change userid userid int(11) comment '用户id';
alter table ulewo_blast_comment change atuserid at_userid int(11) comment 'at用户id';
alter table ulewo_blast_comment change createtime create_time datetime comment '创建时间';
alter table ulewo_blast_comment change sourcefrom source_from varchar(1) comment '来源 A:安卓 P:PC';
alter table ulewo_blast_comment drop column username;
alter table ulewo_blast_comment drop column usericon;
alter table ulewo_blast_comment drop column atusername;


alter table blog_article rename to ulewo_blog;
alter table ulewo_blog change id blog_id int(11) comment '博客ID';
alter table ulewo_blog change userid userid int(11) comment '用户ID';
alter table ulewo_blog change itemid category_id int(11) comment '分类ID';
alter table ulewo_blog change title title varchar(250) comment '标题';
alter table ulewo_blog change summary summary text comment '简介';
alter table ulewo_blog change content content longtext comment '内容';
alter table ulewo_blog change readcount read_count int(11) comment '阅读次数';
alter table ulewo_blog change posttime create_time datetime comment '创建时间';
alter table ulewo_blog change keyword key_word varchar(200) comment '关键字';
alter table ulewo_blog change allowreplay allow_comment varchar(1) default 'Y' comment '允许评论';
alter table ulewo_blog change allimage blog_image text comment '图片';


alter table blog_item rename to ulewo_blog_category;
alter table ulewo_blog_category change id  category_id int(11) comment '分类ID';
alter table ulewo_blog_category change userid userid int(11) comment '用户ID';
alter table ulewo_blog_category change itemname name varchar(50) comment '分类名';
alter table ulewo_blog_category change itemrang rang int(2) comment '排序';

alter table blog_reply rename to ulewo_blog_comment;
alter table ulewo_blog_comment change id id int(11) comment '评论ID';
alter table ulewo_blog_comment change blogid blog_id int(11) comment '博客ID';
alter table ulewo_blog_comment change blogauthor blog_userid int(11) comment '博客作者ID';
alter table ulewo_blog_comment change content content longtext comment '博客内容';
alter table ulewo_blog_comment change posttime create_time datetime comment '创建时间';
alter table ulewo_blog_comment change userid userid int(11) comment '评论人Id';
alter table ulewo_blog_comment drop column username;
alter table ulewo_blog_comment change atuserid at_userid int(11) comment 'at用户Id';
alter table ulewo_blog_comment change sourcefrom source_from varchar(1) comment '来源';

alter table favorite rename to ulewo_collection;
alter table ulewo_collection change userid userid int(11) comment '用户id';
alter table ulewo_collection change articleid article_id int(11) comment '文章ID';
alter table ulewo_collection change title title varchar(250) comment '文章标题';
alter table ulewo_collection change type article_type varchar(1) comment '文章类型A:文章 B:博客';
alter table ulewo_collection drop column partid;

alter table groups rename to ulewo_group;
alter table ulewo_group change id gid int(11) comment '群组ID';
alter table ulewo_group change groupname group_name varchar(100) comment '群组名';
alter table ulewo_group change groupdesc group_desc varchar(300) comment '群组描述';
alter table ulewo_group change groupicon group_icon varchar(300) comment '群组图标';
alter table ulewo_group change groupheadicon group_banner varchar(300) comment '群组banner';
alter table ulewo_group change joinperm join_perm varchar(1) comment '加入权限 Y:无需审核 N:需要审核';
alter table ulewo_group change postperm post_perm varchar(1) comment '发帖权限 A任何人可以发帖 M成员可以发帖';
alter table ulewo_group change groupauthor group_userid int(11) comment '群主';
alter table ulewo_group change createtime create_time datetime comment '创建时间';
alter table ulewo_group change groupnotice group_notice varchar(500) comment '群公告';
alter table ulewo_group change iscommend commend_type varchar(1) default 'N' comment '推荐状态 Y:推荐,N不推荐';
alter table ulewo_group drop column isvalid;
alter table ulewo_group drop column visitcount;



alter table member rename to ulewo_group_member;
alter table ulewo_group_member change id id int(11) comment 'id';
alter table ulewo_group_member change gid gid int(11) comment '群组ID';
alter table ulewo_group_member change userid userid int(11) comment '成员Id';
alter table ulewo_group_member change grade grade int(11) default 0 comment '成员等级';
alter table ulewo_group_member change ismember member_type varchar(1) default 0 comment '成员类型 Y:已经是成员 N:已申请没审核';
alter table ulewo_group_member change jointime join_time datetime  comment '加入时间';

CREATE TABLE `ulewo_private_letter` (
  `letter_id` int(11) NOT NULL AUTO_INCREMENT,
  `send_userid` int(11) NOT NULL COMMENT '发送者id',
  `receive_userid` int(11) NOT NULL COMMENT '接受者ID',
  `content` text COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`letter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内私信';

CREATE TABLE `ulewo_private_letter_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `letter_id` int(11) DEFAULT NULL COMMENT '私信id',
  `userid` int(11) NOT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='私信回复表';


alter table remark rename to ulewo_signin;
alter table ulewo_signin change userid userid int(11) comment '用户ID';
alter table ulewo_signin change marktime sign_time datetime comment '签到时间';
alter table ulewo_signin change markdate sign_date date comment '签到日期';

CREATE TABLE `ulewo_topic_survey` (
  `survey_id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) DEFAULT NULL COMMENT '文章ID',
  `survey_type` varchar(1) DEFAULT NULL COMMENT '调查类型 S:单选 M:多选',
  PRIMARY KEY (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调查表';

CREATE TABLE `ulewo_topic_survey_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `survey_id` int(11) DEFAULT NULL COMMENT '调查ID',
  `title` varchar(300) DEFAULT NULL COMMENT '标题',
  `count` int(11) DEFAULT NULL COMMENT '投票数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调查明细';

CREATE TABLE `ulewo_topic_survey_user` (
  `survey_id` int(11) NOT NULL default '0',
  `survey_dtl_id` int(11) NOT NULL default '0',
  `userid` int(11) NOT NULL default '0',
  PRIMARY KEY  (`survey_id`,`survey_dtl_id`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户投票情况';


alter table article rename to ulewo_topic;
alter table ulewo_topic change id topic_id int(11) comment '主题ID,自增长';
alter table ulewo_topic change gid gid int(11) comment '群组ID';
alter table ulewo_topic change type topic_type int(1) default 0 comment '帖子类型 0:普通帖 1:投票帖';
alter table ulewo_topic change itemid category_id int(11)  comment '分类ID';
alter table ulewo_topic change title title varchar(300)  comment '标题';
alter table ulewo_topic change keyword keyword varchar(200)  comment '关键字';
alter table ulewo_topic change titlestyle title_style_id int(11)  comment '标题样式Id';
alter table ulewo_topic change content content longtext  comment '内容';
alter table ulewo_topic change summary summary text  comment '简介';
alter table ulewo_topic change authorid userid int(11)  comment '作者ID';
alter table ulewo_topic change posttime create_time datetime  comment '创建时间';
alter table ulewo_topic change lastretime last_comment_time datetime  comment '最后回复时间';
alter table ulewo_topic change readnumber read_count int(11)  comment '阅读次数';
alter table ulewo_topic change grade grade int(1)  comment '帖子级别';
alter table ulewo_topic change essence essence varchar(1) default 'N'  comment '是否精华 Y精华 N不是精华';
alter table ulewo_topic drop column syscode;
alter table ulewo_topic drop column subcode;
alter table ulewo_topic change isvalid valid varchar(1) default 'Y'  comment '是否有效 Y:有效 N:无效';
alter table ulewo_topic change allimage topic_image mediumtext comment '主题图片';
alter table ulewo_topic drop column image;


alter table articleitem rename to ulewo_topic_category;
alter table ulewo_topic_category change id category_id int(11) comment '主题ID,自增长';
alter table ulewo_topic_category change gid gid int(11) comment '群组Id';
alter table ulewo_topic_category change itemname name varchar(50) comment '名称';
alter table ulewo_topic_category change itemcode rang int(11) comment '顺序编号';

alter table rearticle rename to ulewo_topic_comment;
alter table ulewo_topic_comment change id id int(11) comment '主题ID,自增长';
alter table ulewo_topic_comment change pid pid int(11) comment '父Id';
alter table ulewo_topic_comment change articleid topic_id int(11) comment '文章Id';
alter table ulewo_topic_comment change gid gid int(11) comment '群组Id';
alter table ulewo_topic_comment change content content longtext comment '内容';
alter table ulewo_topic_comment change authorid userid int(11) comment '作者ID';
alter table ulewo_topic_comment drop column authorname;
alter table ulewo_topic_comment drop column isvalid;
alter table ulewo_topic_comment drop column atUserName;
alter table ulewo_topic_comment drop atUserIcon;
alter table ulewo_topic_comment change atUserId at_userid int(11) comment 'at作者ID';
alter table ulewo_topic_comment change retime create_time datetime comment '回复时间';
alter table ulewo_topic_comment change sourcefrom source_from varchar(1) comment '来源 A:安卓 P:PC';


alter table  user rename to ulewo_user;
alter table ulewo_user change userid userid int(11) comment '作者ID';
alter table ulewo_user change email email varchar(100) comment '邮箱';
alter table ulewo_user change username username varchar(100) comment '用户名';
alter table ulewo_user change password password varchar(100) comment '密码';
alter table ulewo_user change userlittleicon user_icon varchar(250) comment '头像';
alter table ulewo_user drop column userbigicon;
alter table ulewo_user change age age int(11) comment '年龄';
alter table ulewo_user change sex sex varchar(1) comment '性别 M:男 F:女';
alter table ulewo_user change characters characters varchar(255) comment '签名';
alter table ulewo_user change mark mark int(11) comment '积分';
alter table ulewo_user change address address varchar(200) comment '地址';
alter table ulewo_user change work work varchar(200) comment '职业';
alter table ulewo_user change registertime register_time datetime comment '注册时间';
alter table ulewo_user change previsittime pre_visit_time datetime comment '上次登录时间';
alter table ulewo_user change activationcode activation_code varchar(50) comment '激活码';
alter table ulewo_user change isvalid isvalid varchar(1) default 'Y' comment '是否有效';


alter table user_friend  rename to ulewo_user_friend;
alter table ulewo_user_friend change userid userid int(11)  comment '用户ID';
alter table ulewo_user_friend change friendid friend_userid int(11) comment '好友ID';
alter table ulewo_user_friend drop column type;
alter table ulewo_user_friend change createtime create_time datetime comment '创建时间';



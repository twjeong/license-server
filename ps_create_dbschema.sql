create database if not exists user;
create database if not exists contract;
create database if not exists license;
create database if not exists monitor;
create database if not exists log;
create database if not exists email;
create database if not exists billing;

create table if not exists `user`.`user_info` (
 `id` varchar(64) not null comment '아이디',
 `password` varchar(128) not null comment '비밀번호',
 `desc` varchar(255) null comment '상세설명',
 primary key (`id`))
 engine = innodb
 default character set = utf8
 comment = '계정관리테이블';
 
create table if not exists `contract`.`contract_info_json` (
  key_idx int not null comment 'Key ID',
  contract_file_name varchar(128) not null comment '계약 정보 파일명',
  cust_nm varchar(128) null comment '고객사명',
  order_num varchar(64) not null comment '납품계약번호',
  lis_pro_nm varchar(16) null comment '제품명',
  lis_type_oc_up varchar(32) null comment '라이선스 타입',
  core_count smallint null comment '코어 수',
  lis_start_date date null comment '라이선스 시작일',
  lis_end_date date null comment '라이선스 종료일',
  license_status char(1) default '0' null comment '라이선스 상태',
  contract_info_json json comment '계약정보 json',
  primary key (`key_idx`)
  )
 engine = innodb
 default character set = utf8
 comment = '계약정보관리';
  
create table if not exists `license`.`license_info` (
`serial_no` varchar(128) not null comment '시리얼 번호',
`node_id` varchar(128) comment '노드아이드(uuid)',
`key_idx` int null comment 'Key ID',
`contract_file_name` varchar(128) null comment '계약 정보 파일명',
`cust_nm` varchar(128) null comment '고객사명',
`order_num` varchar(64) null comment '납품계약번호',
`license_type` smallint null comment '라이선스 타입',
`component_name` varchar(64) null comment '컴퍼넌트 제품명',
`component_version` varchar(64) null comment '컴퍼넌트 버전',
`ip_address` char(48) null comment 'IP 주소',
`core` smallint null comment 'core 개수',
`instance_nm` varchar(128) null comment 'DP-ORA 인스턴스명',
`license_file` varchar(1024) null comment '라이선스 파일 경로',
`first_usage_time` timestamp null comment '처음 시작 시간',
`last_usage_time` timestamp null comment '마지막 사용 시간',
`hours_of_usage_time` int null comment '제품 사용 시간',
`license_issue_date` date null comment '라이선스 발급일',
`license_expired_date` date null comment '라이선스 만료일',
`license_start_date` date null comment '라이선스(계약정보) 사용 시작일',
`license_end_date` date null comment '라이선스(계약정보) 사용 종료일',
`status` tinyint null comment '라이선스 상태',
`sym_api_yn` char(1) null comment '대칭키 암복호화 API',
`pub_api_yn` char(1) null comment '비대칭키 암복호화 API',
`kewin_api_yn` char(1) null comment 'KE-WIN 복호화 API',
`api_yn` char(1) null comment '암복호화 API',
`kms_yn` char(1) null comment 'KMS 외부 키 연동',
`os_yn` char(1) null comment 'OS 계정별 암복호화 권한 제어',
`lis_os_type` varchar(16) null comment 'OS',
`km_yn` char(1) null comment 'KM 암복호화',
`file_yn` char(1) null comment 'FILE 암복호화',
`hash_yn` char(1) null comment 'HASH 암복호화',
`kms_enc` char(1) null comment 'KMS 암호화 및 BA-SCP 연동',
`kms_pub_key` char(1) null comment 'SCP 외부키 연동',
`kms_pri_key` char(1) null comment '비공개키 연동',
`web_cnt` int null comment '보호 대상 웹서버 등록수 제한(WAPPLES), -1(무제한)',
`lis_exp_act` int null comment '라이선스 만료시 대응 설정, 0, 1, 2',
primary key (`serial_no`),
foreign key (key_idx) references `contract`.`contract_info_json` (key_idx)
)
 engine = innodb
 default character set = utf8
 comment = '제품 라이선스 관리';

create table if not exists `monitor`.`usage_time_info` (
 `serial_no` varchar(128) not null comment '시리얼 번호',
 `date` date not null comment '년 월 일(yymmdd)',
 `cust_nm` varchar(128) null comment '고객사명',
 `minute_of_usage_time` smallint null comment '사용 시간(분)',
 `create_time` timestamp null comment '생성 시간',
 `modify_time` timestamp null comment '수정 시간',
 primary key (`serial_no`, `date`))
 engine = innodb
 default character set = utf8
 comment = '제품 사용시간 관리';
 
create table if not exists `log`.`system_log` (
 `id` int not null auto_increment primary key,
 `facility` smallint null comment 'manager',
 `log_level` smallint null comment '로그레벨',
 `msg` varchar(512) null comment '메시지',
 `create_time` timestamp null comment '생성시간')
 engine = innodb
 default character set = utf8
 comment = '시스템 로그';
 
create table if not exists `log`.`policy_log` (
 `id` int not null auto_increment primary key,
 `facility` smallint null comment 'manager',
 `log_level` smallint null comment '로그레벨',
 `msg` varchar(512) null comment '메시지',
 `create_time` timestamp null comment '생성시간')
 engine = innodb
 default character set = utf8
 comment = '정책 로그';
 
create table if not exists `log`.`event_log` (
 `id` int not null auto_increment primary key,
 `facility` smallint null comment 'manager',
 `log_level` smallint null comment '로그레벨',
 `msg` varchar(512) null comment '메시지',
 `create_time` timestamp null comment '생성시간')
 engine = innodb
 default character set = utf8
 comment = '시스템 로그';
 
create table if not exists `email`.`email_info` (
 `to_address` varchar(128) not null comment '받는 사람 주소',
 `from_address` varchar(128) not null comment '보내는 사람 주소',
 `smtp_address` varchar(128) not null comment '메일 서버 주소',
 `smtp_port` smallint not null comment '메일 서버 포트',
 `username` varchar(128) not null comment '메일 유저',
 `password` varchar(256) not null comment '메일 비밀번호',
 `tls` smallint not null comment 'tls 사용 여부 1(사용) or 0(미사용)',
 primary key (`username`))
 engine = innodb
 default character set = utf8
 comment = '이메일 정보';
 
create table if not exists `billing`.`billing_info` (
	`id` int not null auto_increment primary key,
	`component_name` varchar(128) not null comment '제품명',
	`billing_type` smallint null comment '빌링 타입',
	`core` smallint null comment 'core 개수',
	`hourly_price` int null comment '시간당 가격',
	`base_price` int null comment '기본 가격')
	engine = innodb
	default character set = utf8
	comment = '과금 정책';
 
insert into user.user_info values ('admin', '{bcrypt}$2a$10$nnqQaSYdOgqx0IfEecBxLOVojYhgpnU4RD9PpeRnF2ZuWI1Rfx.Nm', 'ADMIN');
insert into user.user_info values ('dccadmin', '{bcrypt}$2a$10$bzkINZ7WEpARGLccKV5Qk.qWGkkdwCiAMurl6vIU0FTNJQWJMLXNO', 'ADMIN');
commit;


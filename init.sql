-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS zb_cms;

-- 사용자 생성 및 권한 부여
CREATE USER IF NOT EXISTS 'zb_user'@'%' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON zb_cms.* TO 'zb_user'@'%';
FLUSH PRIVILEGES;

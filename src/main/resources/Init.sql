CREATE TABLE account (
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username varchar(1000) NOT NULL,
    name varchar(10) NOT NULL,
    birth date NOT NULL,
    gender varchar(10) NOT NULL,
    password varchar(1000) NOT NULL,
    join_date DATETIME
);

CREATE TABLE account_roles (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    role varchar(10) DEFAULT NULL,
    account_id bigint(20) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY account_id (account_id),
    CONSTRAINT account_roles_ibfk_1 FOREIGN KEY (account_id) REFERENCES account (id)
);

create TABLE association(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(50)  comment '단체이름',
    representative_id BIGINT NOT NULL  comment '대표자',
    create_date DATETIME,
    modify_date DATETIME,
    tel varchar(13),
    office_address varchar(225)  comment '사무실 주소',
    province VARCHAR(12)  comment '사무실 소재지',
    FOREIGN KEY (representative_id) REFERENCES account (id)
) comment '협회';

CREATE TABLE competition (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title varchar(255) comment '대회 타이틀',
    description varchar(2000) comment '대회 설명',
    association_id BIGINT comment '대회 개최 [협회]',
    host BIGINT comment '대회 개최 [개인]',
    fields_id BIGINT,
    played_date DATETIME,
    finished_date DATETIME,
    FOREIGN KEY (association_id) REFERENCES association (id),
    FOREIGN KEY (host) REFERENCES account (id),
    FOREIGN KEY (fields_id) REFERENCES fields (id)
) comment  '대회';

create TABLE club(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(50)  comment '클럽이름',
    representative_id BIGINT NOT NULL  comment '대표자',
    create_date DATETIME,
    modify_date DATETIME,
    tel varchar(13),
    office_address varchar(225)  comment '사무실 주소',
    province VARCHAR(12)  comment '사무실 소재지',
    FOREIGN KEY (representative) REFERENCES account (id)
) comment '클럽';

create TABLE association_club (
    association_id BIGINT NOT NULL ,
    club_id BIGINT NOT NULL,
    FOREIGN KEY (association_id) REFERENCES association (id),
    FOREIGN KEY (club_id) REFERENCES club (id)
) comment '협회 소속 클럽';

create TABLE club_member (
    club_id BIGINT NOT NULL ,
    account_id BIGINT NOT NULL,
    FOREIGN KEY (club_id) REFERENCES club (id),
    FOREIGN KEY (account_id) REFERENCES account (id)
) comment '클럽 소속 멤버';

CREATE TABLE fields (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    total_hole_count INT comment '하프 9 홀, 18홀 = 1라운드 - 대한체육회 파크골프 규정집 => 9의 배수',
    address VARCHAR(2000),
    province VARCHAR(12) comment '지역명, 지역검색 쿼리',
    detail TEXT,
    creator_id BIGINT NOT NULL,
    modifier_id BIGINT,
    create_date DATETIME,
    modify_date DATETIME,
    location POINT,
    FOREIGN KEY (creator_id) REFERENCES account (id),
FOREIGN KEY (modifier_id) REFERENCES account (id)
)comment  '경기장 정보';

CREATE TABLE holes (
    course_seq INT comment '코스 식별자',
    hole_seq INT comment '홀 순번',
    fields_id BIGINT,
    distance INT(4),
    par INT(1),
    detail VARCHAR(2000),
    creator_id BIGINT NOT NULL,
    modifier_id BIGINT,
    create_date DATETIME,
    modify_date DATETIME,
    PRIMARY KEY (course_seq,hole_seq, fields_id),
    FOREIGN KEY (fields_id) REFERENCES fields (id),
    FOREIGN KEY (creator_id) REFERENCES account (id),
    FOREIGN KEY (modifier_id) REFERENCES account (id)
) comment '경기장 홀 별 추가 정보';

CREATE TABLE memo (
    fields_id BIGINT NOT NULL ,
    account_id BIGINT NOT NULL,
    course_seq INT(1),
    hole_seq INT(1),
    content VARCHAR(2000),
    create_date DATETIME,
    modify_date DATETIME,
    PRIMARY KEY (fields_id, account_id, course_seq, hole_seq),
    FOREIGN KEY (fields_id) REFERENCES fields (id),
    FOREIGN KEY (account_id) REFERENCES account (id)
) comment '경기장 홀 메모';

CREATE TABLE game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    competition_id BIGINT,
    host_id BIGINT NOT NULL,
    fields_id BIGINT,
    play_date DATETIME,
    finish_date DATETIME,
    FOREIGN KEY (host) REFERENCES account (id),
    FOREIGN KEY (fields_id) REFERENCES fields (id)
) comment  '매치 정보';

create table game_progress(
    game_id BIGINT,
    turns    INT comment '진행 턴, 경기를 1번 홀에서 시작하지 않을 수 있음',
    half     INT comment '1라운드 = 2하프 = 18홀',
    hole     INT COMMENT '각 하프별로 고정된 9홀의 순번',
    progress_time DATETIME,
    status   VARCHAR(12) comment '현재 진행상태의 상태값.',
    PRIMARY KEY (game_id,turns)
)comment '매치 진행 정보';

CREATE TABLE score (
    game_id BIGINT,
    player_id BIGINT comment '선수',
    turns BIGINT,
    hit INT NOT NULL ,
    penalty INT DEFAULT 0,
    PRIMARY KEY (game_id, player_id,turns),
    FOREIGN KEY (game_id) REFERENCES game (id),
FOREIGN KEY (player_id) REFERENCES account (id)
)comment '선수별 각 홀 점수';



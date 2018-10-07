CREATE TABLE User (
    access_token VARCHAR(200) NOT NULL,
    votes_remain INTEGER NOT NULL,
   PRIMARY KEY (access_token)
);

CREATE TABLE Topic (
    id BIGINT NOT NULL,
    description VARCHAR NOT NULL,
    votes INTEGER NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO User VALUES('user-1', 30);
INSERT INTO User VALUES('user-2', 30);

INSERT INTO Topic VALUES(1, 'This is topic # 1', 0);
INSERT INTO Topic VALUES(2, 'This is topic # 2', 0);



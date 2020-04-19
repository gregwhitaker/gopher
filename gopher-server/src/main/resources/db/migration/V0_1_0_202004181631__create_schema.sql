CREATE TABLE user_role (
    role_id   INTEGER PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL,
    role_desc VARCHAR(200) NOT NULL
);

CREATE TABLE user_profile (
    user_id             VARCHAR(50)     PRIMARY KEY,
    user_username       VARCHAR(100)    NOT NULL,
    user_pwdhash        VARCHAR(100)    NOT NULL,
    user_firstname      VARCHAR(100),
    user_lastname       VARCHAR(100),
    user_email          VARCHAR(100),
    user_active         BOOLEAN         DEFAULT TRUE NOT NULL,
    role_id             INT             NOT NULL,
    user_created_at     TIMESTAMP       NOT NULL DEFAULT now(),
    user_lastlogin_at   TIMESTAMP,

  CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES user_role (role_id)
);
CREATE UNIQUE INDEX idx_user_profile_username ON user_profile (user_username);
CREATE INDEX idx_user_profile_active ON user_profile (user_active);

CREATE TABLE link (
    link_golink      VARCHAR(250)   PRIMARY KEY,
    link_url         VARCHAR(250)   NOT NULL,
    link_active      BOOLEAN        NOT NULL DEFAULT TRUE,
    link_public      BOOLEAN        NOT NULL DEFAULT FALSE,
    link_created_at  TIMESTAMP      NOT NULL DEFAULT now(),
    link_created_by  VARCHAR(50)    NOT NULL REFERENCES user_profile (user_id)
);
CREATE INDEX idx_link_active ON link (link_active);
CREATE INDEX idx_link_created_by ON link (link_created_by);

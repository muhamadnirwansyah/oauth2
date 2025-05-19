CREATE TABLE oauth2_registered_client (
    id VARCHAR(100) PRIMARY KEY,
    client_id VARCHAR(100) NOT NULL,
    client_id_issued_at TIMESTAMP NOT NULL,
    client_secret VARCHAR(200),
    client_secret_expires_at TIMESTAMP,
    client_name VARCHAR(200) NOT NULL,
    client_authentication_methods TEXT NOT NULL,
    authorization_grant_types TEXT NOT NULL,
    redirect_uris TEXT,
    scopes TEXT,
    client_settings TEXT NOT NULL,
    token_settings TEXT NOT NULL
);

CREATE TABLE oauth2_authorization (
    id VARCHAR(100) PRIMARY KEY,
    registered_client_id VARCHAR(100) NOT NULL,
    principal_name VARCHAR(200) NOT NULL,
    authorization_grant_type VARCHAR(100) NOT NULL,
    authorized_scopes TEXT,
    attributes TEXT,
    state VARCHAR(500),
    authorization_code_value TEXT,
    authorization_code_issued_at TIMESTAMP,
    authorization_code_expires_at TIMESTAMP,
    authorization_code_metadata TEXT,
    access_token_value TEXT,
    access_token_issued_at TIMESTAMP,
    access_token_expires_at TIMESTAMP,
    access_token_metadata TEXT,
    access_token_type VARCHAR(100),
    access_token_scopes TEXT,
    refresh_token_value TEXT,
    refresh_token_issued_at TIMESTAMP,
    refresh_token_expires_at TIMESTAMP,
    refresh_token_metadata TEXT,
    id_token_value TEXT,
    id_token_issued_at TIMESTAMP,
    id_token_expires_at TIMESTAMP,
    id_token_metadata TEXT
);

CREATE TABLE oauth2_authorization_consent (
    registered_client_id VARCHAR(100) NOT NULL,
    principal_name VARCHAR(200) NOT NULL,
    authorities TEXT NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);

--1 get code
-- http://localhost:10000/oauth2/authorize?response_type=code&client_id=angular-client&redirect_uri=http://localhost:4200/callback&scope=read
--2 authorization code get token
-- http://localhost:10000/oauth2/token
--header 'Content-Type: application/x-www-form-urlencoded'
--header 'Authorization: Basic YW5ndWxhci1jbGllbnQ6c2VjcmV0' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'code=-MtioXKW30kaYv2wiTQWnqpiQNJEG3zeouLSK5e6D9ElBKaWgzNQlLHjOK7klBltycMlcjTK8FQOuXrmdjLj0AW1Xsg9R7aF3f81rM9FlS3PLNOi5hIRNhwXQGQwVyO4' \
--data-urlencode 'redirect_uri=http://localhost:4200/callback'
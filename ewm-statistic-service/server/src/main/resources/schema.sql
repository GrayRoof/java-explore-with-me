CREATE TABLE IF NOT EXISTS hits (
        id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
        app VARCHAR(100),
        uri VARCHAR(150),
        ip VARCHAR(30),
        timestamp TIMESTAMP WITHOUT TIME ZONE,
        CONSTRAINT hits_id_pk PRIMARY KEY (id)
);
CREATE TABLE attendees (
    id INT generated always as IDENTITY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(4) NOT NULL,
    PRIMARY KEY (id)
)
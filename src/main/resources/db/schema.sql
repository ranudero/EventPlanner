CREATE TABLE attendees (
    id INT generated always as IDENTITY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(4) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE event (
    id INT generated always as IDENTITY,
    name VARCHAR(255) NOT NULL,
    start DATE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE inviteperevent (
    id INT generated always as IDENTITY,
    event_id INT NOT NULL,
    attendee_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (event_id) REFERENCES event(id),
    FOREIGN KEY (attendee_id) REFERENCES attendees(id)
);
CREATE TABLE IF NOT EXISTS role
(
    role_id BIGINT,
    name    VARCHAR(30) NOT NULL,
    CONSTRAINT PK_role PRIMARY KEY (role_id),
    CONSTRAINT UQ_role_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS admin
(
    admin_id   BIGINT,
    email      VARCHAR(70) NOT NULL,
    password   VARCHAR(70) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL,
    role_id    BIGINT      NOT NULL,
    is_locked  BOOLEAN     NOT NULL DEFAULT FALSE,
    CONSTRAINT PK_admin PRIMARY KEY (admin_id),
    CONSTRAINT UQ_admin_email UNIQUE (email),
    CONSTRAINT FK_admin_role FOREIGN KEY (role_id) REFERENCES role (role_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS employee
(
    employee_id  BIGINT,
    first_name   VARCHAR(30) NOT NULL,
    last_name    VARCHAR(30) NOT NULL,
    email        VARCHAR(40) NOT NULL,
    phone_number VARCHAR(40) NOT NULL,
    password     VARCHAR(70) NOT NULL,
    is_locked    BOOLEAN     NOT NULL DEFAULT FALSE,
    role_id      BIGINT      NOT NULL,
    CONSTRAINT PK_employee PRIMARY KEY (employee_id),
    CONSTRAINT UQ_employee_email UNIQUE (email),
    CONSTRAINT UQ_employee_phone_number UNIQUE (phone_number),
    CONSTRAINT FK_employee_role FOREIGN KEY (role_id) REFERENCES role (role_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS office_building
(
    office_building_id BIGINT,
    city               VARCHAR(255) NOT NULL,
    street             VARCHAR(255) NOT NULL,
    house              VARCHAR(255) NOT NULL,
    name               VARCHAR(255) NOT NULL,
    CONSTRAINT PK_office_building PRIMARY KEY (office_building_id),
    CONSTRAINT UQ_office_building_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS meeting_room
(
    meeting_room_id    BIGINT,
    number             INT,
    floor              INT,
    info               VARCHAR(255),
    office_building_id BIGINT NOT NULL,
    CONSTRAINT PK_meeting_room PRIMARY KEY (meeting_room_id),
    CONSTRAINT FK_meeting_room_office_building
        FOREIGN KEY (office_building_id) REFERENCES office_building (office_building_id) ON DELETE CASCADE
);

CREATE TYPE booking_status AS ENUM ('CREATED', 'IN_PROGRESS', 'FINISHED', 'CANCELED');

CREATE TABLE IF NOT EXISTS booking
(
    booking_id      BIGINT,
    date            DATE           NOT NULL,
    time            TIME           NOT NULL,
    --15 30 60 (1) 90 (1:30) 120 (2) 240 (4) 360 (6) 480 (8)
    duration        INT            NOT NULL,
    status          booking_status NOT NULL,
    employee_id     BIGINT         NOT NULL,
    meeting_room_id BIGINT         NOT NULL,
    CONSTRAINT PK_booking PRIMARY KEY (booking_id),
    CONSTRAINT FK_booking_meeting_room
        FOREIGN KEY (meeting_room_id) REFERENCES meeting_room (meeting_room_id) ON DELETE CASCADE,
    CONSTRAINT FK_booking_employee
        FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE
);

INSERT INTO role (role_id, name)
VALUES (1, 'ADMIN');
INSERT INTO role (role_id, name)
VALUES (2, 'EMPLOYEE');

INSERT INTO admin (admin_id, email, password, first_name, last_name, role_id, is_locked)
VALUES (1, 'admin@gmail.com', '$2a$10$ecGZcqzz2.BW884wo/6REuUyL/68oo4dJA66FliY.EYjPg5llaXZy', 'Rick', 'Sanchez', 1, false);

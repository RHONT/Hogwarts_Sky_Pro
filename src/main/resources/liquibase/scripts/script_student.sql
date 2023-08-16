-- liquibase formatted sql
-- changeset rhont:1

CREATE INDEX student_name_idx ON student (name);
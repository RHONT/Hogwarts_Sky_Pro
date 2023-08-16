-- liquibase formatted sql
-- changeset rhont:1

CREATE INDEX color_name_idx ON faculty (name, color);
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create table if not exists types
(
    name        varchar     not null,
    UNIQUE      (name),
    PRIMARY KEY (name)
    );
create table if not exists animals
(
    id          uuid         not null,
    name        varchar(100) not null,
    type        varchar,
    UNIQUE      (id),
    PRIMARY KEY (id)
    );
ALTER TABLE animals ADD CONSTRAINT fk_roles FOREIGN KEY (type) references types(name)

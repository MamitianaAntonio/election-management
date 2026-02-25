create table candidate (
    id serial primary key,
    name text not null
);

create table voter (
    id serial primary key ,
    name text not null
);

CREATE TYPE vote_type AS ENUM ('VALID', 'BLANK', 'NULL');

CREATE TABLE vote (
  id SERIAL PRIMARY KEY,
  candidate_id INT REFERENCES candidate(id),
  voter_id INT NOT NULL REFERENCES voter(id),
  vote_type vote_type NOT NULL
);
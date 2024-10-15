-- init.sql
CREATE TABLE technician (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(30) NOT NULL
);

CREATE TABLE service (
                         id SERIAL PRIMARY KEY,
                         address VARCHAR(30) NOT NULL,
                         description VARCHAR(100),
                         start_date TIMESTAMP NOT NULL,
                         end_date TIMESTAMP NOT NULL,
                         technician_id BIGINT REFERENCES technician(id)
);

-- Create Author table
CREATE TABLE Author (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
);

-- Create Book table
CREATE TABLE Book (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(100) NOT NULL,
                      author_id INT,
                      CONSTRAINT fk_author
                          FOREIGN KEY(author_id)
                              REFERENCES Author(id)
);

-- Insert values into Author table
INSERT INTO Author (name) VALUES ('J.K. Rowling');
INSERT INTO Author (name) VALUES ('George R.R. Martin');
INSERT INTO Author (name) VALUES ('J.R.R. Tolkien');

-- Insert values into Book table
INSERT INTO Book (title, author_id) VALUES ('Harry Potter and the Philosopher''s Stone', 1);
INSERT INTO Book (title, author_id) VALUES ('Harry Potter and the Chamber of Secrets', 1);
INSERT INTO Book (title, author_id) VALUES ('A Game of Thrones', 2);
INSERT INTO Book (title, author_id) VALUES ('A Clash of Kings', 2);
INSERT INTO Book (title, author_id) VALUES ('The Hobbit', 3);
INSERT INTO Book (title, author_id) VALUES ('The Lord of the Rings', 3);
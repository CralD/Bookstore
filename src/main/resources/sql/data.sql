-- data.sql

-- Insert genres
INSERT INTO genres (name) VALUES ('Fiction');
INSERT INTO genres (name) VALUES ('Non-Fiction');

-- Insert authors
INSERT INTO authors (name) VALUES ('F. Scott Fitzgerald');
INSERT INTO authors (name) VALUES ('Malcolm Gladwell');

-- Insert books
INSERT INTO books (title, author_id, genre_id, price, quantity_available) VALUES
('The Great Gatsby', 1, 1, 10.99, 100),
('Outliers', 2, 2, 15.99, 150),
('To Kill a Mockingbird', 1, 1, 12.99, 200),
('Blink', 2, 2, 14.99, 180),
('The Catcher in the Rye', 1, 1, 11.99, 120);

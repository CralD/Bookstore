# Online Bookstore API

This is a RESTful API for managing an online bookstore. The API allows users to perform CRUD operations on books, authors, and genres. Additionally, users can search for books by title, author, or genre. The application uses **Spring Boot** for backend development, **Hibernate** as the ORM for interacting with a relational database, and **JUnit/Mockito** for testing.

## Features

- **Books**: Perform CRUD operations on books with attributes like title, author, genre, price, and quantity.
- **Authors**: Manage author data (CRUD).
- **Genres**: Manage genre data (CRUD).
- **Search**: Search for books by title, author, or genre.

## Technology Stack

- **Spring Boot**: Framework for creating the API.
- **Hibernate**: ORM for database interactions.
- **H2 Database**: In-memory database for quick testing.
- **JUnit & Mockito**: For unit testing and service mocking.

## Endpoints

### Books

- `GET /api/books`: Get a list of all books.
- `GET /api/books/{id}`: Get a specific book by ID.
- `POST /api/books`: Add a new book.
- `PUT /api/books/{id}`: Update an existing book.
- `DELETE /api/books/{id}`: Delete a book.
- `GET /api/books/search/title?title={title}`: Search books by title.
- `GET /api/books/search/author?author={author}`: Search books by author name.
- `GET /api/books/search/genre?genre={genre}`: Search books by genre.

### Authors

- `GET /api/authors`: Get a list of all authors.
- `GET /api/authors/{id}`: Get a specific author by ID.
- `POST /api/authors`: Add a new author.
- `PUT /api/authors/{id}`: Update an existing author.
- `DELETE /api/authors/{id}`: Delete an author.

### Genres

- `GET /api/genres`: Get a list of all genres.
- `GET /api/genres/{id}`: Get a specific genre by ID.
- `POST /api/genres`: Add a new genre.
- `PUT /api/genres/{id}`: Update an existing genre.
- `DELETE /api/genres/{id}`: Delete a genre.

## Prerequisites

- Java 17+
- Maven
- Git

## Setup and Run

1. **Clone the Repository**  
   Clone this repository to your local machine:

   ```bash
   git clone https://github.com/CralD/Bookstore
   cd online-bookstore-api

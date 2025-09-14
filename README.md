# Book Cataloging System — Deliverable 1

**Team 15** · Haeri Kyoung & Shiva Gowtham Kumar Vidiyala

Two language implementations of a simple book catalog with a web user interface and SQLite storage.

## Languages and Features
- **Ruby**: Sinatra web app with Sequel and Ruby blocks for summaries
- **Java**: MVC web application using Spring Boot framework with layered architecture

## Core Functions
- Add a book with title, author, genre, and year  
- Delete a book
- List and search by title, author, or genre  
- Simple reports showing counts by genre and by author

## Repository Layout
```plaintext
MSCS632_GroupProject_BookCatalog/
  README.md
  ruby/
    Gemfile
    app.rb
    config.ru
    db/
      migrate_init.rb
    views/
      index.erb
    vendor/           (bundled gems, auto created)
  csharp/             
  Java/
    CatalogBOOK/
      README.md
      pom.xml
      src/
        main/
          java/com/system/catalogbook/
            CatalogbookApplication.java
            dto/
              BookRequest.java
              ReportItem.java
            model/
              Book.java
            repo/
              BookRepository.java
              BookSpecs.java
            service/
              BookService.java
            web/
              BookController.java
          resources/
            static/css/
              addform.css
              main.css
              search.css
            templates/
              books/
                addform.html
                booksreport.html
                list.html
                managebooks.html
                searchbook.html
                searchresult.html
              reports/
                booksfinalreport.html
              layout.html
            application.properties
    jar/
      catalogbook-0.0.1-SNAPSHOT.jar         

### How to Run — Ruby App
### Prerequisites
- Ruby 3.2 or newer  
- Bundler  
- SQLite

### Setup
```bash
cd ruby
bundle install
bundle exec ruby db/migrate_init.rb
```

### Start the Server
Using rackup:
```bash
bundle exec rackup -p 4567 -o 0.0.0.0
```

Open http://localhost:4567


### Routes
- `GET /` — list UI and add form  
- `POST /add` — create a book  
- `GET /search` — query with `q=` and `by=title|author|genre`  
- `GET /report/genre` — JSON counts by genre  
- `GET /report/author` — JSON counts by author


## How to Run — Java App
### Prerequisites
- Java 17 or newer
- Maven 3.9+

### Setup
- cd CatalogBOOK
- mvn clean install

### Start the Server
mvn spring-boot:run

or 

Go to Jar folder and run below command
java -jar target/CatalogBOOK-0.0.1-SNAPSHOT.jar


### Access the Application
http://localhost:8080

### To access Database(In Memory)
http://localhost:8080/h2-console

use below credentials
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: sa

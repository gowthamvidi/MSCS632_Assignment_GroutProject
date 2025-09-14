# Book Cataloging System — Deliverable 1

**Team 15** · Haeri Kyoung & Shiva Gowtham Kumar Vidiyala

Two language implementations of a simple book catalog with a web user interface and SQLite storage.

## Languages and Features
- **Ruby**: Sinatra web app with Sequel and Ruby blocks for summaries

## Core Functions
- Add a book with title, author, genre, and year  
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
  csharp/             (to be added next)
```

## How to Run — Ruby App

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

### Start the Server (pick one)
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
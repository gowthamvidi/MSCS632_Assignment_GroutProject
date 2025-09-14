Book Cataloging System — Deliverable 1

Team 15 - Haeri Kyoung & Shiva Gowtham Kumar Vidiyala

Two language implementations of a simple book catalog with a web user interface and SQLite storage.

Languages and features
- C sharp: ASP dot NET Core web app with Entity Framework Core and LINQ queries
- Ruby: Sinatra web app with Sequel and Ruby blocks for summaries

Core functions
- add a book with title author genre and year
- list and search by title author or genre
- simple report count by genre and by author

Repository layout
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

How to run — Ruby app

Prerequisites
- Ruby 3.2 or newer
- Bundler
- SQLite

Setup
cd ruby
bundle install
bundle exec ruby db/migrate_init.rb

Start the server (pick one)
# using rackup
bundle exec rackup -p 4567 -o 0.0.0.0

# or run the app file with Puma
bundle exec ruby app.rb -p 4567 -o 0.0.0.0 -s puma

Open http://localhost:4567

Routes
- GET /            list UI and add form
- POST /add        create a book
- GET /search      q= text filter, by= title or author or genre
- GET /report/genre   JSON counts by genre
- GET /report/author  JSON counts by author
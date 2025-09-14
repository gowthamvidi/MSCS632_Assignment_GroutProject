require "bundler/setup"
require "sinatra"
require "sequel"
require "json"

set :bind, "0.0.0.0"
set :port, 4567

DB = Sequel.sqlite("books.db")
Books = DB[:books]

get "/" do
  @books = Books.order(:title).all
  erb :index
end

post "/add" do
  Books.insert(
    title: params["title"].to_s,
    author: params["author"].to_s,
    genre: params["genre"].to_s,
    year: params["year"].to_i
  )
  redirect "/"
end

get "/search" do
  q  = params["q"].to_s
  by = params["by"].to_s
  ds = Books
  unless q.strip.empty?
    case by
    when "author" then ds = ds.where(Sequel.like(:author, "%#{q}%"))
    when "genre"  then ds = ds.where(Sequel.like(:genre,  "%#{q}%"))
    else               ds = ds.where(Sequel.like(:title,  "%#{q}%"))
    end
  end
  content_type :json
  ds.order(:title).all.to_json
end

get "/report/genre" do
  content_type :json
  Books.group_and_count(:genre).order(Sequel.desc(:count)).all.to_json
end

get "/report/author" do
  content_type :json
  Books.group_and_count(:author).order(Sequel.desc(:count)).all.to_json
end


require "bundler/setup"
require "sequel"

DB = Sequel.sqlite("books.db")
DB.create_table? :books do
  primary_key :id
  String  :title,  null: false
  String  :author, null: false
  String  :genre,  null: false
  Integer :year
end
puts "migrated"

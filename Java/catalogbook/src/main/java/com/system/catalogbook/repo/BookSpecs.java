package com.system.catalogbook.repo;

import com.system.catalogbook.model.Book;
import org.springframework.data.jpa.domain.Specification;

public final class BookSpecs {
    private BookSpecs() {}

    public static Specification<Book> titleContains(String title) {
        if (title == null || title.isBlank()) return null;
        String q = "%" + title.trim().toLowerCase() + "%";
        return (root, cq, cb) -> cb.like(cb.lower(root.get("title")), q);
    }

    public static Specification<Book> authorContains(String author) {
        if (author == null || author.isBlank()) return null;
        String q = "%" + author.trim().toLowerCase() + "%";
        return (root, cq, cb) -> cb.like(cb.lower(root.get("author")), q);
    }

    public static Specification<Book> genreEquals(String genre) {
        if (genre == null || genre.isBlank()) return null;
        String g = genre.trim().toLowerCase();
        return (root, cq, cb) -> cb.equal(cb.lower(root.get("genre")), g);
    }

    public static Specification<Book> yearFrom(Integer yearFrom) {
        if (yearFrom == null) return null;
        return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("publicationYear"), yearFrom);
    }

    public static Specification<Book> yearTo(Integer yearTo) {
        if (yearTo == null) return null;
        return (root, cq, cb) -> cb.lessThanOrEqualTo(root.get("publicationYear"), yearTo);
    }
}

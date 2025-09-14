package com.system.catalogbook.service;

import com.system.catalogbook.dto.BookRequest;
import com.system.catalogbook.dto.ReportItem;
import com.system.catalogbook.model.Book;
import com.system.catalogbook.repo.BookRepository;
import com.system.catalogbook.repo.BookSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repo;

    public Book create(BookRequest req) {
        Book b = Book.builder()
                .title(req.getTitle())
                .author(req.getAuthor())
                .genre(req.getGenre())
                .publicationYear(req.getPublicationYear())
                .build();
        return repo.save(b);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Page<Book> search(String title, String author, String genre,
                             Integer yearFrom, Integer yearTo, int page, int size) {

        Specification<Book> spec = Specification.where(null);

        if (hasText(title)) {
            spec = spec.and(BookSpecs.titleContains(title));
        }
        if (hasText(author)) {
            spec = spec.and(BookSpecs.authorContains(author));
        }
        if (hasText(genre)) {
            spec = spec.and(BookSpecs.genreEquals(genre));
        }
        if (yearFrom != null) {
            spec = spec.and(BookSpecs.yearFrom(yearFrom));
        }
        if (yearTo != null) {
            spec = spec.and(BookSpecs.yearTo(yearTo));
        }

        return repo.findAll(spec, PageRequest.of(page, size, Sort.by("title").ascending()));
    }

    public List<ReportItem> reportByGenre() {
        // Simple in-memory aggregation (fine for small datasets). For large DBs, write a custom query.
        return repo.findAll().stream()
                .collect(java.util.stream.Collectors.groupingBy(Book::getGenre, java.util.stream.Collectors.counting()))
                .entrySet().stream()
                .map(e -> new ReportItem(e.getKey(), e.getValue()))
                .sorted(java.util.Comparator.comparing(ReportItem::key, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public List<ReportItem> reportByAuthor() {
        return repo.findAll().stream()
                .collect(java.util.stream.Collectors.groupingBy(Book::getAuthor, java.util.stream.Collectors.counting()))
                .entrySet().stream()
                .map(e -> new ReportItem(e.getKey(), e.getValue()))
                .sorted(java.util.Comparator.comparing(ReportItem::key, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public List<Book> findAllBooks() {
        return repo.findAll(Sort.by("title").ascending());
    }


}

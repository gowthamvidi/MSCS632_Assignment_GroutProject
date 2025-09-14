package com.system.catalogbook.web;

import com.system.catalogbook.dto.BookRequest;
import com.system.catalogbook.dto.ReportItem;
import com.system.catalogbook.model.Book;
import com.system.catalogbook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BookUiController {

    private final BookService service;
    @GetMapping("/")
    public String home() { return "layout"; }

    @GetMapping({"/books_search"})
    public String bookSearch() {
        return "books/searchbook";
    }

    @GetMapping({"/books_report"})
    public String bookReport() {
        return "books/booksreport";
    }

    @GetMapping({"/searchbooks"})
    public String list(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model
    ) {
        Page<Book> result = service.search(title, author, genre, yearFrom, yearTo, page, size);
        model.addAttribute("page", result);
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("genre", genre);
        model.addAttribute("yearFrom", yearFrom);
        model.addAttribute("yearTo", yearTo);
        return "books/searchresult";
    }

    @GetMapping("/books/addnew")
    public String addBookFormRedirect() {
        return "books/addform";
    }

    @GetMapping("/books/new")
    public String createForm(Model model) {
        model.addAttribute("book", new BookRequest());
        return "books/form";
    }

    @PostMapping("/booksadded")
    public String addBooks(@ModelAttribute("book") BookRequest req) {
        service.create(req);
        return "redirect:/";
    }

    @PostMapping("/books_delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/booksmanage";
    }

    @GetMapping("/reports_genre")
    public String reportByGenre(Model model) {
        java.util.List<ReportItem> data = service.reportByGenre();
        model.addAttribute("items", data);
        model.addAttribute("heading", "Books by Genre");
        model.addAttribute("keyLabel", "Genre");
        return "reports/booksfinalreport";
    }

    @GetMapping("/reports_author")
    public String reportByAuthor(Model model) {
        java.util.List<ReportItem> data = service.reportByAuthor();
        model.addAttribute("items", data);
        model.addAttribute("heading", "Books by Author");
        model.addAttribute("keyLabel", "Author");
        return "reports/booksfinalreport";
    }

    @GetMapping("/booksmanage")
    public String listAllBooks(Model model) {
        model.addAttribute("books", service.findAllBooks());
        return "books/managebooks";
    }
}

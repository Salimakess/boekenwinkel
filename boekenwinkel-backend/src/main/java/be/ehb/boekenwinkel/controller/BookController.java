
package be.ehb.boekenwinkel.controller;

import be.ehb.boekenwinkel.model.Author;
import be.ehb.boekenwinkel.model.Book;
import be.ehb.boekenwinkel.repository.BookRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import be.ehb.boekenwinkel.repository.AuthorRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository,
                          AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {

        System.out.println(">>> TEST POST BOOK <<<");

        for (Author author : book.getAuthors()) {
            System.out.println("NOM AUTEUR RECU : " + author.getName());
        }
        for (Author author : book.getAuthors()) {
            System.out.println("AVANT SAVE : " + author.getName());
        }
        for (Author author : book.getAuthors()) {
            authorRepository.save(author);
        }

        return bookRepository.save(book);
    }
    @PostMapping("/test-author")
    public Author testAuthor() {
        Author author = new Author();
        author.setName("Auteur Test");

        System.out.println("TEST AUTEUR : " + author.getName());

        return authorRepository.save(author);
    }
}


package be.ehb.boekenwinkel.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(nullable = false, unique = true)
    private String isbn;

    private String title;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @Column(length = 2000)
    private String description;

    private BigDecimal price;

    private int stock;

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Book() {


    }
}

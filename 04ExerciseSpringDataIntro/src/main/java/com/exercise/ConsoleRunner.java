package com.exercise;

import com.exercise.entities.Author;
import com.exercise.entities.Book;
import com.exercise.repositories.AuthorRepository;
import com.exercise.repositories.BookRepository;
import com.exercise.servises.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private BufferedReader reader;

    @Autowired
    public ConsoleRunner(SeedService seedService,
                         BookRepository bookRepository,
                         AuthorRepository authorRepository) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        System.out.print("Please enter 1 if you want to insert data or 2 if you want to choose query: ");
        int line = Integer.parseInt(reader.readLine());

        int numberOfQuery = 0;

        if (line == 1) {
            insertData();
        } else {
            System.out.print("Please enter which query do you want to check (number between 1 and 4): ");
            numberOfQuery = choseWhatToDo(numberOfQuery);
        }

        switch (numberOfQuery) {
            case 1:
                this.booksAfter2000();
                break;
            case 2:
                this.allAuthorsWithBookBefore1990();
                break;
            case 3:
                this.allAuthorsOrderedByBookCount();
                break;
            case 4:
                this.booksFromAuthorOrderedByBookReleaseDateDescThenByBookTitleAsc();
                break;
            default:
                System.out.println("Sorry, but you have to enter query number between 1 and 4.Try again!");
        }
    }

    private void insertData() throws IOException {
        this.seedService.seedAll();
    }

    private int choseWhatToDo(int numberOfQuery) {
        try {
            numberOfQuery = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberOfQuery;
    }

    private void booksAfter2000() {
        LocalDate year2000 = LocalDate.of(2000, 12, 31);

        List<Book> books = this.bookRepository.findByReleaseDateAfter(year2000);

        books.forEach(b -> System.out.println(b.getTitle()));

        int count = this.bookRepository.countByReleaseDateAfter(year2000);

        System.out.println("Total count: " + count);
    }

    private void allAuthorsWithBookBefore1990() {
        LocalDate year1990 = LocalDate.of(1990, 1, 1);

        List<Author> authors = this.authorRepository.findDistinctByBooksReleaseDateBefore(year1990);

        authors.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    private void allAuthorsOrderedByBookCount() {
        List<Author> authors = this.authorRepository.findAll();

        authors
                .stream()
                .sorted((l, r) -> r.getBooks().size() - l.getBooks().size())
                .forEach(author ->
                        System.out.printf("%s %s -> %d%n",
                                author.getFirstName(),
                                author.getLastName(),
                                author.getBooks().size()));
    }

    private void booksFromAuthorOrderedByBookReleaseDateDescThenByBookTitleAsc() {
        String firstName = "George";
        String lastName = "Powell";
        List<Author> authors = this.authorRepository.findByFirstNameAndLastName(firstName, lastName);

        List<Book> books = this.bookRepository.findByAuthor((authors.get(0)));

        books
                .stream()
                .sorted((l, r) -> {
                    if (r.getReleaseDate().compareTo(l.getReleaseDate()) == 0) {
                        return l.getTitle().compareTo(r.getTitle());
                    } else {
                        return r.getReleaseDate().compareTo(l.getReleaseDate());
                    }
                })
                .forEach(b -> System.out.println(b.getTitle() + " " + b.getReleaseDate() + " " + b.getCopies()));
    }
}

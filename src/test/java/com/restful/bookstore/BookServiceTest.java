package com.restful.bookstore;

import com.restful.bookstore.entities.Author;
import com.restful.bookstore.entities.Book;
import com.restful.bookstore.entities.Publishing;
import com.restful.bookstore.repositories.BookRepository;
import com.restful.bookstore.services.AuthorService;
import com.restful.bookstore.services.BookService;
import com.restful.bookstore.services.PublishingService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository repository;

    @Mock
    private AuthorService authorService;

    @Mock
    private PublishingService publishingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> expectedBooks = new ArrayList<>();
        Book book1 = new Book();
        Book book2 = new Book();

        book1.setIsbn("ISBN123");
        book2.setIsbn("ISBN456");

        expectedBooks.add(book1);
        expectedBooks.add(book2);

        when(repository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getAllBooks();

        assertNotNull(expectedBooks);
        assertEquals(expectedBooks.size(), actualBooks.size());
    }

    @Test
    public void testGetBookByIsbn() {
        Book expectedBook = new Book();

        expectedBook.setIsbn("ISBN123");

        when(repository.findById("ISBN123")).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookService.getBookByIsbn("ISBN123");

        assertNotNull(actualBook);
        assertEquals(expectedBook.getIsbn(), actualBook.getIsbn());
    }

    @Test
    public void testGetBookByTitle() {
        Book expectedBook = new Book();

        expectedBook.setTitle("Title 1");

        when(repository.findByTitle("Title 1")).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookService.getBookByTitle("Title 1");

        assertNotNull(actualBook);
        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
    }

    @Test
    public void testGetBookByGenre() {
        Book expectedBook = new Book();

        expectedBook.addGenre("Fiction");
        expectedBook.addGenre("Mystery");

        //When calling the findByGenre method, it can be done passing any element of the genre array, so in this case either Fiction or Mystery.
        when(repository.findByGenre("Mystery")).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookService.getBookByGenre("Mystery");

        assertNotNull(actualBook);
        assertEquals(expectedBook.getGenre(), actualBook.getGenre());
    }

    @Test
    public void testGetBookByYear() {
        Book expectedBook = new Book();

        expectedBook.setDateOfPublication(LocalDate.of(2023, 8, 17));

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        when(repository.findByDateOfPublicationBetween(startDate, endDate)).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookService.getBookByYear(2023);

        assertNotNull(actualBook);
        assertEquals(expectedBook.getDateOfPublication(), actualBook.getDateOfPublication());
    }

    @Test
    public void testCreateBook() {
        Author mockAuthor = new Author(1L, "Author 1");
        Publishing mockPublishing = new Publishing(1L, "Publishing 1");

        Book expectedBook = new Book();
        expectedBook.setIsbn("ISBN123");
        expectedBook.setTitle("Title 1");
        expectedBook.setDateOfPublication(LocalDate.of(2023, 8, 17));
        expectedBook.setAuthor(mockAuthor);
        expectedBook.setPublishing(mockPublishing);

        when(repository.save(any())).thenReturn(expectedBook);

        Book actualBook = bookService.createBook(expectedBook);

        assertNotNull(actualBook);
        assertEquals("ISBN123", actualBook.getIsbn());
        assertEquals("Title 1", actualBook.getTitle());
        assertEquals(LocalDate.of(2023, 8, 17), actualBook.getDateOfPublication());
        assertEquals(mockAuthor, actualBook.getAuthor());
        assertEquals(mockPublishing, actualBook.getPublishing());
    }

    @Test
    public void testUpdateBook() {
        Author mockAuthor = new Author(1L, "Author 1");
        Publishing mockPublishing = new Publishing(1L, "Publishing 1");

        Book mockBook = new Book();
        mockBook.setIsbn("ISBN123");
        mockBook.setTitle("Title 1");
        mockBook.setDateOfPublication(LocalDate.of(2023, 8, 17));
        mockBook.setAuthor(mockAuthor);
        mockBook.setPublishing(mockPublishing);

        when(repository.save(any())).thenReturn(mockBook);

        when(repository.findById("ISBN123")).thenReturn(Optional.of(mockBook));

        Book updatedBook = mockBook;
        updatedBook.setTitle("Updated title");

        bookService.updateBook("ISBN123", updatedBook);

        assertEquals(mockBook.getIsbn(), updatedBook.getIsbn());

        verify(repository, times(1)).save(updatedBook);
    }

    @Test
    public void testDeleteBook() {
        Book mockBook = new Book();

        mockBook.setIsbn("ISBN123");

        when(repository.save(any())).thenReturn(mockBook);

        when(repository.findById("ISBN123")).thenReturn(Optional.of(mockBook));

        boolean result = bookService.deleteBook("ISBN123");

        assertTrue(result);
        verify(repository, times(1)).delete(mockBook);
    }

}

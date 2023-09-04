package com.restful.bookstore;

import com.restful.bookstore.entities.Author;
import com.restful.bookstore.entities.Book;
import com.restful.bookstore.repositories.AuthorRepository;
import com.restful.bookstore.services.AuthorService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTest {

    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> expectedAuthors = new ArrayList<>();
        expectedAuthors.add(new Author(1L, "Author 1"));
        expectedAuthors.add(new Author(2L, "Author 2"));

        when(repository.findAll()).thenReturn(expectedAuthors);

        List<Author> actualAuthors = authorService.getAllAuthors();

        assertNotNull(actualAuthors);
        assertEquals(expectedAuthors.size(), actualAuthors.size());
    }

    @Test
    public void testGetAuthorById() {
        Author expectedAuthor = new Author();

        when(repository.findById(1L)).thenReturn(Optional.of(expectedAuthor));

        Author actualAuthor = authorService.getAuthorById(1L);

        assertNotNull(actualAuthor);
        assertEquals(expectedAuthor.getId(), actualAuthor.getId());
    }

    @Test
    public void testGetAuthorByName() {
        Author expectedAuthor = new Author();

        expectedAuthor.setName("Author 1");

        when(repository.findByName("Author 1")).thenReturn(Optional.of(expectedAuthor));

        Author actualAuthor = authorService.getAuthorByName("Author 1");

        assertNotNull(actualAuthor);
        assertEquals(expectedAuthor.getName(), actualAuthor.getName());
    }

    @Test
    public void testGetBooksByAuthor() {
        Author expectedAuthor = new Author();
        expectedAuthor.setName("Author 1");
        List<Book> expectedBooks = new ArrayList<>();

        Book book1 = new Book();
        book1.setAuthor(expectedAuthor);

        expectedBooks.add(book1);

        Book book2 = new Book();
        book2.setAuthor(expectedAuthor);

        expectedBooks.add(book2);

        expectedAuthor.setBooks(expectedBooks);

        when(repository.findById(1L)).thenReturn(Optional.of(expectedAuthor));

        List<Book> actualBooks = authorService.getBooksByAuthor(1L);

        assertNotNull(actualBooks);
        assertEquals(expectedBooks.size(), actualBooks.size());
        assertEquals(expectedBooks.get(0).getAuthor().getName(), actualBooks.get(0).getAuthor().getName());
    }

    @Test
    public void testCreateAuthor() {
        Author expectedAuthor = new Author(1L, "Author1");

        when(repository.save(any())).thenReturn(expectedAuthor);

        Author actualAuthor = authorService.createAuthor(expectedAuthor);

        assertNotNull(actualAuthor);
        assertEquals(expectedAuthor.getName(), actualAuthor.getName());
    }

    @Test
    public void testDeleteAuthor() {
        Author mockAuthor = new Author(1L, "Author1");

        when(repository.findById(1L)).thenReturn(Optional.of(mockAuthor));

        boolean result = authorService.deleteAuthor(1L);

        assertTrue(result);

        verify(repository, times(1)).delete(mockAuthor);
    }
}

package com.restful.bookstore;

import com.restful.bookstore.entities.Book;
import com.restful.bookstore.entities.Publishing;
import com.restful.bookstore.repositories.PublishingRepository;
import com.restful.bookstore.services.PublishingService;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PublishingServiceTest {

    @Mock
    private PublishingRepository repository;

    @InjectMocks
    private PublishingService publishingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPublishing() {
        List<Publishing> expectedPublishing = new ArrayList<>();
        expectedPublishing.add(new Publishing(1L, "Publishing 1"));
        expectedPublishing.add(new Publishing(2L, "Publishing 2"));

        when(repository.findAll()).thenReturn(expectedPublishing);

        List<Publishing> actualPublishing = publishingService.getAllPublishing();

        assertNotNull(actualPublishing);
        assertEquals(expectedPublishing.size(), actualPublishing.size());
    }

    @Test
    public void testGetPublishingById() {
        Publishing expectedPublishing = new Publishing();

        when(repository.findById(1L)).thenReturn(Optional.of(expectedPublishing));

        Publishing actualPublishing = publishingService.getPublishingById(1L);

        assertNotNull(actualPublishing);
        assertEquals(expectedPublishing.getId(), actualPublishing.getId());
    }

    @Test
    public void testGetPublishingByName() {
        Publishing expectedPublishing = new Publishing();

        expectedPublishing.setName("Publishing 1");

        when(repository.findByName("Publishing 1")).thenReturn(Optional.of(expectedPublishing));

        Publishing actualPublishing = publishingService.getPublishingByName("Publishing 1");

        assertNotNull(actualPublishing);
        assertEquals(expectedPublishing.getName(), actualPublishing.getName());
    }

    @Test
    public void testGetBooksByPublishing() {
        Publishing expectedPublishing = new Publishing();
        expectedPublishing.setName("Publishing 1");
        List<Book> expectedBooks = new ArrayList<>();

        Book book1 = new Book();
        Book book2 = new Book();

        book1.setPublishing(expectedPublishing);
        book2.setPublishing(expectedPublishing);

        expectedBooks.add(book1);
        expectedBooks.add(book2);

        expectedPublishing.setBooks(expectedBooks);

        when(repository.findById(1L)).thenReturn(Optional.of(expectedPublishing));

        List<Book> actualBooks = publishingService.getBooksByPublishing(1L);

        assertNotNull(actualBooks);
        assertEquals(expectedBooks.size(), actualBooks.size());
        assertEquals(expectedBooks.get(0).getPublishing().getName(), actualBooks.get(0).getPublishing().getName());
    }

    @Test
    public void testCreatePublishing() {
        Publishing expectedPublishing = new Publishing();

        expectedPublishing.setName("Publishing 1");

        when(repository.save(any())).thenReturn(expectedPublishing);

        Publishing actualPublishing = publishingService.createPublishing(expectedPublishing);

        assertNotNull(actualPublishing);
        assertEquals(expectedPublishing.getName(), actualPublishing.getName());
    }

    @Test
    public void testUpdatePublishing() {
        Publishing mockPublishing = new Publishing();

        mockPublishing.setName("Mock name");

        when(repository.save(any())).thenReturn(mockPublishing);

        when(repository.findById(1L)).thenReturn(Optional.of(mockPublishing));

        Publishing updatedPublishing = mockPublishing;

        updatedPublishing.setName("Updated name");

        publishingService.updatePublishing(1L, updatedPublishing);

        assertEquals(mockPublishing.getId(), updatedPublishing.getId());

        verify(repository, times(1)).save(updatedPublishing);
    }

    @Test
    public void testDeletePublishing() {
        Publishing mockPublishing = new Publishing();

        when(repository.findById(1L)).thenReturn(Optional.of(mockPublishing));

        boolean result = publishingService.deletePublishing(1L);

        assertTrue(result);

        verify(repository, times(1)).delete(mockPublishing);
    }
}

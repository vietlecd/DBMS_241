package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.models.Author;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.AuthorRepository;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.responses.BookProjection;
import com.project.shopapp.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<BookDTO> findAll(Map<String, Object> params) {
        // Fetch list of Book using JPA with filters
        List<Book> bookEntities = bookRepository.findByParamsAndTypeCode(params);
        List<BookDTO> result = new ArrayList<>();

        // Convert each Book to BookDTO
        for (Book item : bookEntities) {
            BookDTO book = new BookDTO();
            book.setBTitle(item.getTitle());
            book.setDescription(item.getDescription());
            book.setBookID(item.getBookID());
            book.setPrice(item.getPrice());
            book.setPublishYear(item.getPublishyear());
            book.setCoverImage(item.getCoverimage());

            // Lấy tên và mô tả của các danh mục liên quan
            Set<Category> categories = item.getCategories();
            String categoryNames = categories.stream()
                    .map(Category::getName)
                    .collect(Collectors.joining(", "));
            String categoryDescriptions = categories.stream()
                    .map(Category::getCatedescription)
                    .collect(Collectors.joining(", "));

            // Set giá trị tên và mô tả của danh mục vào BookDTO
//            book.setCatedescription(categoryDescriptions);
//            book.setCatedescription(categoryNames);

            result.add(book);
        }

        return result;
    }
    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        // Create a new Book entity and set basic fields
        Book book = new Book();
        book.setTitle(bookDTO.getBTitle());
        book.setDescription(bookDTO.getDescription());
        book.setCoverimage(bookDTO.getCoverImage());
        book.setPublishyear(bookDTO.getPublishYear());
        book.setPrice(bookDTO.getPrice());

        Set<Author> authors = new HashSet<>();
        for (String username : bookDTO.getAuthorName()) {
            Optional<Author> existingAuthor = authorRepository.findAuthorByFullname(username);

            if (existingAuthor.isPresent()) {
                Author author = existingAuthor.get();
                authors.add(author);

                author.getBookSet().add(book);
            }
        }

        book.setAuthorList(authors);

        // Save the book entity
        book = bookRepository.save(book);

        // Convert back to BookDTO to return
        BookDTO result = new BookDTO();
        result.setBookID(book.getBookID());
        result.setBTitle(book.getTitle());
        result.setDescription(book.getDescription());
//        result.setCateDescription(book.getCategories());
        result.setCoverImage(book.getCoverimage());
        result.setPublishYear(book.getPublishyear());
        result.setPrice(book.getPrice());
        result.setAuthorName(bookDTO.getAuthorName());

        return result;
    }

    @Override
    public boolean deleteBookByTitle(String title) {
        // Tìm sách theo title
        Book book = bookRepository.findByTitle(title);
        if (book != null) {
            bookRepository.delete(book); // Xóa sách
            return true;
        }
        return false; // Không tìm thấy sách
    }

    @Override
    public List<BookProjection> getBooksByAuthor(String authorName) {
        return bookRepository.findBookByAuthorName(authorName);
    }
}

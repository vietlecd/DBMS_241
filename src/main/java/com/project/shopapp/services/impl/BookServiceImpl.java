package com.project.shopapp.services.impl;


import java.util.*;
import java.util.stream.Collectors;

import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.components.BookDTOFactory;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.BookRepository;


import com.project.shopapp.repositories.CategoryRepository;

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



    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<BookDTO> findAll(Map<String, Object> params) {
        // Fetch list of Book using JPA with filters
        List<Book> bookEntities = bookRepository.findByParamsAndTypeCode(params);
        List<BookDTO> result = new ArrayList<>();

        // Convert each Book to BookDTO
        for (Book item : bookEntities) {
            BookDTO book = new BookDTO();
            book.setTitle(item.getTitle());

            book.setDescription(item.getDescription());
            book.setBookID(item.getBookID());
            book.setPrice(item.getPrice());
            book.setPublishYear(item.getPublishyear());
            book.setCoverImage(item.getCoverimage());

            // Lấy tên và mô tả của các danh mục liên quan
            Set<Category> categories = item.getCategories();
            String categoryNames = categories.stream()

                    .map(Category::getNamecategory)

                    .collect(Collectors.joining(", "));
            String categoryDescriptions = categories.stream()
                    .map(Category::getCatedescription)
                    .collect(Collectors.joining(", "));

            // Set giá trị tên và mô tả của danh mục vào BookDTO

            book.setCateDescription(categoryDescriptions);
            book.setNamecategory(categoryNames);


            result.add(book);
        }

        return result;
    }
    @Override
    public BookDTO createBook(BookDTO bookDTO) {

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setCoverimage(bookDTO.getCoverImage());
        book.setPublishyear(bookDTO.getPublishYear());
        book.setStatus(bookDTO.getStatus());
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
        //DTO thành Entity
        Set<Category> categories = new HashSet<>();
        if (bookDTO.getNamecategory() != null && bookDTO.getCateDescription() != null) {
            Category category = categoryRepository.findByNamecategoryAndCatedescription(
                    bookDTO.getNamecategory(), bookDTO.getCateDescription());
            if (category == null) {
                category = new Category();
                category.setNamecategory(bookDTO.getNamecategory());
                category.setCatedescription(bookDTO.getCateDescription());
                category = categoryRepository.save(category);
            }
            categories.add(category);
        }
        book.setCategories(categories);
        book = bookRepository.save(book);

        //ENtity thành DTO
        BookDTO result = new BookDTO();
        result.setBookID(book.getBookID());
        result.setTitle(book.getTitle());
        result.setDescription(book.getDescription());
        result.setCoverImage(book.getCoverimage());
        result.setPublishYear(book.getPublishyear());
        result.setPrice(String.valueOf(book.getPrice()));
        result.setStatus(book.getStatus());
        result.setPrice(book.getPrice());

        if (!book.getCategories().isEmpty()) {
            Category category = book.getCategories().iterator().next();
            result.setNamecategory(category.getNamecategory());
            result.setCateDescription(category.getCatedescription());
        }

        return result;
    }

    @Override
    public boolean deleteBookBybookID(Long bookID) {
        // Tìm sách theo title

        Book book = bookRepository.findByBookID(bookID);

        if (book != null) {
            bookRepository.delete(book); // Xóa sách
            return true;
        }
        return false; // Không tìm thấy sách
    }

    @Override

    public boolean sendBookRequestCheck(String title) {
        // Giả định rằng findByTitle trả về một Book thay vì Optional<Book>
        // Loại bỏ khoảng trắng và ký tự xuống hàng khỏi title
        title = title.trim();
        Book book = bookRepository.findByTitle(title);

        if (book != null) {
            // Giả định status là String, kiểm tra nếu status khác "true"
            if (book.getStatus() == null || !book.getStatus().equals("true")) {
                // Set status thành "false" để biểu thị yêu cầu đã được gửi
                book.setStatus("false");
                bookRepository.save(book);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean acceptBookRequestCheck(String title) {
        Book book = bookRepository.findByTitle(title);
        if (book != null && "false".equals(book.getStatus())) {
            // Nếu sách tồn tại và status là "false", set status thành "true"
            book.setStatus("true");
            bookRepository.save(book);
            return true;
        }
        return false;
    }

 /*   @Override
    public List<BookDTO> getBooksWithPendingRequest() {
        List<Book> pendingBooks = bookRepository.findByStatus("false");
        System.out.println("Pending Books Count: " + pendingBooks.size());

        return pendingBooks.stream()
                .map(book -> bookDTOFactory.createBookDTO(book.getTitle(), book.getStatus()))
                .collect(Collectors.toList());
    }*/

    @Override
    public boolean denyBookRequestCheck(String title) {
        Book book = bookRepository.findByTitle(title);
        if (book != null) {
            bookRepository.delete(book); // Xóa sách
            return true;
        }
        return false; // Không tìm thấy sách
    }
    public List<BookProjection> getBooksByAuthor(String authorName) {
        return bookRepository.findBookByAuthorName(authorName);
    }




}

package com.project.shopapp.services.impl;


import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.helpers.AuthorHelper;
import com.project.shopapp.helpers.BookResponseHelper;
import com.project.shopapp.helpers.UploadDriveHelper;
import com.project.shopapp.models.*;
import com.project.shopapp.repositories.*;
import com.project.shopapp.responses.BookAuthorResponse;
import com.project.shopapp.services.IBookService;
import com.project.shopapp.utils.CheckExistedUtils;
import com.project.shopapp.utils.StringSimilarityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements IBookService {

    private BookRepository bookRepository;
    private BookResponseHelper bookResponseHelper;
    private CategoryRepository categoryRepository;
    private UploadDriveHelper uploadDriveHelper;
    private AuthorHelper authorHelper;
    private CheckExistedUtils checkExistedUtils;
    private AuthorRepository authorRepository;

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
            book.setPublishyear(item.getPublishyear());
            book.setCoverimage(item.getCoverimage());

            // Lấy tên và mô tả của các danh mục liên quan

            Set<Category> categories = item.getCategories();

            // Chuyển đổi Set<Category> thành Set<String> cho tên và mô tả danh mục
            Set<String> categoryNames = categories.stream()
                    .map(Category::getNamecategory)
                    .collect(Collectors.toSet());
            String categoryDescriptions = categories.stream()
                    .map(Category::getCatedescription)
                    .collect(Collectors.joining(", "));

            // Set giá trị tên và mô tả của danh mục vào BookDTO

            book.setCatedescription(categoryDescriptions);
            book.setPdf(item.getPdf());
            //book.setNamecategory(categoryNames);


            result.add(book);
        }

        return result;
    }
    @Override
    public ResponseEntity<?> createBook(BookDTO bookDTO, MultipartFile pdf, MultipartFile image, User user) throws IOException {

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setPublishyear(bookDTO.getPublishyear());
        book.setStatus("false");
        book.setPrice(bookDTO.getPrice());
        book.setTotalpage(bookDTO.getTotalpage());
        book.setUploader(user);

        checkExistedUtils.checkFileExists(image, "image");
        checkExistedUtils.checkFileExists(pdf, "pdf");

        String file = uploadDriveHelper.upDrive(pdf);
        String img = uploadDriveHelper.upDrive(image);
        book.setPdf(file);
        book.setCoverimage(img);

        Set<Author> authors = new HashSet<>();
        for (String username : bookDTO.getUsername()) {
            Optional<Author> existingAuthor = authorHelper.getAuthorByUsernameAndStatus(username, 0);

            if (existingAuthor.isPresent()) {
                Author author = existingAuthor.get();
                authors.add(author);
                author.getBookSet().add(book);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khong tim thay ten username cua author tren");
            }
        }
        book.setAuthorList(authors);

        Set<String> categoryNames = bookDTO.getNamecategory();
        Set<Category> categories = new HashSet<>();

        for (String categoryName : categoryNames) {
            List<Category> existingCategories = categoryRepository.findByNamecategory(categoryName);
            if (categoryName == null) {
                throw new DataNotFoundException("Category not found: " + categoryName);
            }
            if (!existingCategories.isEmpty()) {
                Category category = existingCategories.get(0);
                categories.add(category);
                category.getBooks().add(book);
            }
        }

        book.setCategories(categories);

        bookRepository.save(book);

        return null;
    }

    @Override
    public boolean deleteBookBybookID(Integer bookID) {
        Book book = bookRepository.findByBookID(bookID);

        if (book != null) {
            book.getCategories().clear();
            bookRepository.save(book);

            bookRepository.delete(book);

            return true;
        }
        return false;
    }

    @Override
    public boolean acceptBookRequestCheck(Integer bookID) {
        Book book = bookRepository.findByBookID(bookID);
        if (book != null && "false".equals(book.getStatus())) {
            book.setStatus("true");
            bookRepository.save(book);

            return true;
        }
        return false;
    }

    @Override
    public boolean denyBookRequestCheck(Integer bookID) {
        Book book = bookRepository.findByBookID(bookID);

        if (book != null) {
            bookRepository.delete(book);
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<?> getBookBought(String username) {
        List<Book> bookList = bookRepository.findBookBought(username);
        List<BookAuthorResponse> res = bookResponseHelper.bookListGet(bookList);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> getBookWritten(String username) {
        List<Book> bookList = bookRepository.findBooksByUsername(username);
        List<BookAuthorResponse> res = bookResponseHelper.bookListGet(bookList);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> getBooksByAuthor(String authorName) {
        List<Author> authors = authorRepository.findAll();

        List<Author> matchedAuthors = new ArrayList<>();
        double similarityThreshold = 0.8;

        for (Author author : authors) {
            double similarity = StringSimilarityUtil.calculateSimilarity(authorName, author.getUser().getFullName());
            if (similarity >= similarityThreshold) {
                matchedAuthors.add(author);
            }
        }

        checkExistedUtils.checkObjectExisted(matchedAuthors, "Author");

        List<Book> books = new ArrayList<>();
        for (Author author : matchedAuthors) {
            books.addAll(bookRepository.findBooksByUsername(author.getUser().getUsername()));
        }

        List<BookAuthorResponse> responses = bookResponseHelper.bookListGet(books);

        return ResponseEntity.ok(responses);

    }
}
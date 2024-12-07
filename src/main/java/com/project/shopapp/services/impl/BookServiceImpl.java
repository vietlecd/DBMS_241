package com.project.shopapp.services.impl;


import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.helpers.AuthorHelper;
import com.project.shopapp.helpers.BookResponseHelper;
import com.project.shopapp.helpers.BookViewHelper;
import com.project.shopapp.helpers.UploadDriveHelper;
import com.project.shopapp.models.*;
import com.project.shopapp.repositories.*;
import com.project.shopapp.responses.BookAuthorResponse;
import com.project.shopapp.services.IBookService;
import com.project.shopapp.services.IDriveService;
import com.project.shopapp.services.INotificationService;
import com.project.shopapp.utils.CheckExistedUtils;
import com.project.shopapp.utils.NotificationUtils;
import com.project.shopapp.utils.StringSimilarityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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
    private INotificationService notificationService;
    private NotificationUtils notificationUtils;
    private IDriveService driveService;
    private BookViewHelper bookViewHelper;

    @Override
    public Integer count_view_book(Integer bookId) {
        Book book = bookRepository.findByBookID(bookId);
        checkExistedUtils.checkObjectExisted(book, "Book");
        return bookViewHelper.getViewCountFromRedis(bookId);
    }

    @Override
    public void readingBook (Integer bookId) {
        Book book = bookRepository.findByBookID(bookId);
        checkExistedUtils.checkObjectExisted(book, "Book");
        if (book != null) {
            bookViewHelper.incrementViewCountInRedis(bookId);
        }
    }

    @Override
    public List<BookAuthorResponse> findByCategory(String params) {
        List<Book> bookEntities = bookRepository.GetBooksByParams(params);
        checkExistedUtils.checkObjectExisted(bookEntities, "Book");
        List<BookAuthorResponse> result = bookResponseHelper.bookListGet(bookEntities);
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
        book.setAuthor_name(bookDTO.getAuthor_name());

        checkExistedUtils.checkFileExists(image, "image");
        checkExistedUtils.checkFileExists(pdf, "pdf");

        Set<Author> authors = new HashSet<>();
        for (String username : bookDTO.getUsername()) {
            Optional<Author> existingAuthor = authorHelper.getAuthorByUsernameAndStatus(username, 1);
            checkExistedUtils.checkObjectExisted(existingAuthor, "Author");

            Author author = existingAuthor.get();
            authors.add(author);
            author.getBookSet().add(book);
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

        String file = uploadDriveHelper.upPDFDrive(pdf);
        String img = uploadDriveHelper.upIMGDrive(image);
        book.setPdf(file);
        book.setCoverimage(img);

        book.setCategories(categories);

        bookRepository.save(book);

        return null;
    }

    @Override
    public boolean deleteBookBybookID(Integer bookID) {
        Book book = bookRepository.findByBookID(bookID);

        if (book != null) {
            driveService.deleteFileOrFolder(book.getCoverimage());
            driveService.deleteFileOrFolder(book.getPdf());

            book.getCategories().clear();
            bookRepository.deleteAuthorBookByBookID(bookID);

            bookRepository.delete(book);

            return true;
        }
        return false;
    }

    @Override
    public boolean acceptBookRequestCheck(Integer bookID) {
        Book book = bookRepository.findByBookID(bookID);
        if (book != null && "false".equals(book.getStatus())) {
            User user = book.getUploader();
            book.setStatus("true");
            String message = notificationUtils.return_book(book.getTitle(), "accept");
            notificationService.createNotification(message, user);
            bookRepository.save(book);

            return true;
        }
        return false;
    }

    @Override
    public boolean denyBookRequestCheck(Integer bookID) {
        Book book = bookRepository.findByBookID(bookID);

        if (book != null) {
            User user = book.getUploader();

            String message = notificationUtils.return_book(book.getTitle(), "deny");
            notificationService.createNotification(message, user);
            bookRepository.deleteAuthorBookByBookID(bookID);

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
        List<Book> bookList = bookRepository.findBooksByAuthorUsername(username);
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
        matchedAuthors.forEach(author -> {
            List<Book> foundBooks = bookRepository.findBooksByAuthorUsername(author.getUser().getUsername());
            if (foundBooks != null && !foundBooks.isEmpty()) {
                books.addAll(foundBooks);
            }
        });


        List<BookAuthorResponse> responses = bookResponseHelper.bookListGet(books);

        return ResponseEntity.ok(responses);

    }

    @Override
    public ResponseEntity<?> countBookWritten(String username) {
        Integer res = bookRepository.count_book_written(username);
        return ResponseEntity.ok(res);
    }

    @Override
    public List<?> getFreeBook() {
        List<Book> res = bookRepository.get_free_books();
        checkExistedUtils.checkObjectExisted(res, "Free Book List");
        List <BookAuthorResponse> responses = bookResponseHelper.bookListGet(res);
        return responses;
    }

    @Override
    public List<?> getRecommendBook() {
        List<Book> res = bookRepository.get_recommend_books();
        checkExistedUtils.checkObjectExisted(res, "Free Book List");
        List <BookAuthorResponse> responses = bookResponseHelper.bookListGet(res);
        return responses;
    }
}
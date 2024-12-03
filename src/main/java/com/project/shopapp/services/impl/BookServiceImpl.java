package com.project.shopapp.services.impl;


import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.helpers.UploadDriveHelper;
import com.project.shopapp.models.*;
import com.project.shopapp.repositories.*;
import com.project.shopapp.responses.BookAuthorResponse;
import com.project.shopapp.responses.BookProjection;
import com.project.shopapp.responses.Impl.BookProjectionImpl;
import com.project.shopapp.services.IBookService;
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
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;
    private PointPayRepository pointPayRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private DriveService driveService;
    private UploadDriveHelper uploadDriveHelper;

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

        if (pdf.isEmpty()) {
            throw new DataNotFoundException("khong tim thay filePDF");
        }

        if (image.isEmpty()) {
            throw new DataNotFoundException("khong tim thay image");
        }

        String file = uploadDriveHelper.upDrive(pdf);
        String img = uploadDriveHelper.upDrive(image);
        book.setPdf(file);
        book.setCoverimage(img);

        Set<Author> authors = new HashSet<>();
        for (String username : bookDTO.getUsername()) {
            Author existingAuthor = authorRepository.findAuthorByUser_Username(username);

            if (existingAuthor != null) {
                authors.add(existingAuthor);
                existingAuthor.getBookSet().add(book);
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
    public ResponseEntity<?> getBooksByAuthor(String authorName) {

        List<BookProjection> rawResults = bookRepository.findBookByAuthorName(authorName);

        if (rawResults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found any book by author's name: " + authorName);
        }

        Map<Integer, BookProjection> groupedBooks = new HashMap<>();

        rawResults.forEach(result -> {
            if (!groupedBooks.containsKey(result.getBookID())) {
                groupedBooks.put(result.getBookID(), new BookProjectionImpl(result));
            }
            groupedBooks.get(result.getBookID()).getCategories().add(result.getCategories().get(0));
        });

        return ResponseEntity.ok(new ArrayList<>(groupedBooks.values()));
    }

//    @Override
//    public ResponseEntity<?> getBookBought(User user) {
//        List<BookProjection> booKBought = bookRepository.findBookBoughtByUserId(user.getId());
//        if (booKBought.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khong tim thay sach nao da mua");
//        }
//
//        return ResponseEntity.ok(booKBought);
//
//    }

    @Override
    public ResponseEntity<?> getBookWritten(User user) {
        List<BookAuthorResponse> bookList = bookRepository.findBooksByUserId(user.getId());


        if (bookList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khong tim thay sach nao da viet");
        }

        return ResponseEntity.ok(bookList);
    }
}
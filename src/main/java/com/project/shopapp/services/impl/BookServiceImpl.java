package com.project.shopapp.services.impl;


import java.util.*;
import java.util.stream.Collectors;

import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.models.*;
import com.project.shopapp.repositories.*;


import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.responses.BookProjection;

import com.project.shopapp.responses.Impl.BookProjectionImpl;
import com.project.shopapp.services.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            book.setNamecategory(categoryNames);


            result.add(book);
        }

        return result;
    }
    @Override
    public ResponseEntity<?> createBook(BookDTO bookDTO) {
        // Tạo một đối tượng Book từ BookDTO
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setCoverimage(bookDTO.getCoverimage());
        book.setPublishyear(bookDTO.getPublishyear());

        book.setStatus("false");
        book.setPrice(bookDTO.getPrice());
        book.setTotalpage(bookDTO.getTotalpage());

        // Xử lý tác giả (Author) và thêm vào Book
       Set<Author> authors = new HashSet<>();
        for (String username : bookDTO.getAuthorName()) {
            Optional<Author> existingAuthor = authorRepository.findAuthorByFullname(username);

            if (existingAuthor.isPresent()) {
                Author author = existingAuthor.get();
                authors.add(author);

                author.getBookSet().add(book);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tac gia chua dang ki ten");
            }
        }

        book.setAuthorList(authors);

        // Xử lý danh mục (Category) và thêm vào Book
        Set<Category> categories = new HashSet<>();
        for (String namecategory : bookDTO.getNamecategory()) {
            List<Category> existingCategories = categoryRepository.findByNamecategory(namecategory);

            if (!existingCategories.isEmpty()) {
                // Nếu category tồn tại, lấy danh mục đầu tiên và thêm nó vào tập categories
                Category category = existingCategories.get(0);
                categories.add(category);
                category.getBooks().add(book);
            }
        }


// Gán danh sách categories vào book



        book.setCategories(categories);

        // Lưu đối tượng Book vào cơ sở dữ liệu
        book = bookRepository.save(book);

        // Chuyển đổi từ Book Entity sang BookDTO
        BookDTO result = new BookDTO();
        result.setBookID(book.getBookID());
        result.setTitle(book.getTitle());
        result.setDescription(book.getDescription());
        result.setCoverimage(book.getCoverimage());
        result.setPublishyear(book.getPublishyear());
        result.setPrice(book.getPrice());

       result.setNamecategory(bookDTO.getNamecategory());
       result.setCatedescription(bookDTO.getCatedescription());
       result.setAuthorName(bookDTO.getAuthorName());
       result.setTotalpage(book.getTotalpage());

        // Lấy danh sách tên tác giả từ danh sách Author trong Book

        // Lấy thông tin danh mục nếu tồn tại


        return ResponseEntity.ok(result);
    }


    @Override
    public boolean deleteBookBybookID(Integer bookID) {
        // Tìm sách theo bookID
        Book book = bookRepository.findByBookID(bookID);

        if (book != null) {
            // Xóa tất cả các liên kết giữa Book và Category trong bảng trung gian
            book.getCategories().clear();
            bookRepository.save(book); // Cập nhật lại thay đổi vào database

            // Xóa sách
            bookRepository.delete(book);

            return true;
        }
        return false; // Không tìm thấy sách
    }



    @Override
    public boolean acceptBookRequestCheck(Integer bookID) {
        Book book = bookRepository.findByBookID(bookID);
        if (book != null && "false".equals(book.getStatus())) {
            // Nếu sách tồn tại và status là "false", set status thành "true"
            book.setStatus("true");
            if (book.getAuthorList() == null || !book.getAuthorList().equals(book.get)) {
                // Nếu không phải hoặc chưa có tác giả, set username làm tác giả
                book.setAuthor(username);
            }
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    @Override
    public boolean denyBookRequestCheck(Integer bookID) {
        Book book = bookRepository.findByBookID(bookID);

        if (book != null) {
            // Xóa tất cả các liên kết giữa Book và Category trong bảng trung gian
            book.getCategories().clear();
            bookRepository.save(book); // Cập nhật lại thay đổi vào database

            // Xóa sách
            bookRepository.delete(book);

            return true;
        }
        return false; // Không tìm thấy sách
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


    @Override
    public ResponseEntity<?> getBookBought(User user) {
        List<BookProjection> booKBought = bookRepository.findBookBoughtByUserId(user.getId());


        if (booKBought.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khong tim thay sach nao da mua");
        }

        return ResponseEntity.ok(booKBought);

    }
}

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
    public BookDTO createBook(BookDTO bookDTO) {
        // Tạo một đối tượng Book từ BookDTO
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setCoverimage(bookDTO.getCoverimage());
        book.setPublishyear(bookDTO.getPublishyear());
        book.setStatus(bookDTO.getStatus());
        book.setPrice(bookDTO.getPrice());

        // Xử lý tác giả (Author) và thêm vào Book
       /* Set<Author> authors = new HashSet<>();
        for (String username : bookDTO.getAuthorName()) {
            Optional<Author> existingAuthor = authorRepository.findAuthorByFullname(username);

            if (existingAuthor.isPresent()) {
                Author author = existingAuthor.get();
                authors.add(author);

                author.getBookSet().add(book);
            }
        }

        book.setAuthorList(authors);*/

        // Xử lý danh mục (Category) và thêm vào Book
        Set<Category> categories = new HashSet<>();
        for (String catename : bookDTO.getNamecategory()) {
            Optional<Category> existingCategory = categoryRepository.findByNamecategory(catename);

            if (existingCategory.isPresent()) {
                // Nếu category tồn tại, thêm nó vào tập categories
                Category category = existingCategory.get();
                categories.add(category);
                category.getBooks().add(book);
            } else {
                // Nếu không tồn tại, tạo mới category và thêm vào tập categories
                Category newCategory = new Category();
                newCategory.setNamecategory(catename);
                newCategory.setCatedescription(bookDTO.getCatedescription()); // Giả định rằng catedescription có thể lấy từ bookDTO
                newCategory = categoryRepository.save(newCategory);

                categories.add(newCategory);
                newCategory.getBooks().add(book);
            }
        }

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
        result.setStatus(book.getStatus());
       result.setNamecategory(bookDTO.getNamecategory());
       result.setCatedescription(bookDTO.getCatedescription());

        // Lấy danh sách tên tác giả từ danh sách Author trong Book

        // Lấy thông tin danh mục nếu tồn tại


        return result;
    }


    @Override
    public boolean deleteBookBybookID(Long bookID) {
        // Tìm sách theo bookID
        Book book = bookRepository.findById(bookID).orElse(null);

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
    public boolean acceptBookRequestCheck(Long bookID) {
        Book book = bookRepository.findByBookID(bookID);
        if (book != null && "false".equals(book.getStatus())) {
            // Nếu sách tồn tại và status là "false", set status thành "true"
            book.setStatus("true");
            bookRepository.save(book);
            return true;
        }
        return false;
    }



    @Override
    public boolean denyBookRequestCheck(Long bookID) {
        Book book = bookRepository.findById(bookID).orElse(null);

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
    public List<BookProjection> getBooksByAuthor(String authorName) {
        return bookRepository.findBookByAuthorName(authorName);
    }




}

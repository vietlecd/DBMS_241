package com.project.shopapp.services.impl;


import com.project.shopapp.DTO.BookmarkDTO;
import com.project.shopapp.DTO.ReviewDTO;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.BookMark;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.repositories.BookmarksRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IBookMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookMarkService implements IBookMarkService {

    @Autowired
    private BookmarksRepository bookmarksRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> addBookmarkToBook(Long bookID, BookmarkDTO bookmarkDTO, User user) {
        // Tạo mới bookmark với sách và số trang
        Optional<Book> optionalBook = bookRepository.findById(bookID);
        if (!optionalBook.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy Book với ID: " + bookID, HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Không tìm thấy User với username: ");
        }

        Book book = optionalBook.get();
        // Tạo mới bookmark chuyển từ DTO vào entity
        BookMark bookmark = new BookMark();
        bookmark.setPageNumber(bookmarkDTO.getPageNumber());
        bookmark.setBook(book);
        bookmark.setUser(user);



        bookmarksRepository.save(bookmark);
        book.getBookmarks().add(bookmark);

        // Định dạng createDate sang ISO-8601
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


        // Chuyển đổi Entity sang DTO
        BookmarkDTO savedbookmarkDTO = new BookmarkDTO();
        savedbookmarkDTO.setPageNumber(bookmark.getPageNumber());
        savedbookmarkDTO.setUsername(user.getUsername());
        savedbookmarkDTO.setCreateDate(
                bookmark.getCreateDate().format(isoFormatter) // Định dạng LocalDateTime thành chuỗi
        );
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedbookmarkDTO);
    }
/*

    public List<BookmarkDTO> getAllBookmarks() {
        // Lấy toàn bộ danh sách bookmark và chuyển đổi sang DTO
        return bookmarksRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<BookmarkDTO> getBookmarksByBook(Long id) {
        // Lấy danh sách bookmark theo sách (id)
        return bookmarksRepository.findById(id)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteBookmark(Long markID) {
        // Kiểm tra và xóa bookmark theo markID
        if (!bookmarksRepository.existsById(markID)) {
            throw new IllegalArgumentException("Không tìm thấy bookmark với ID: " + markID);
        }
        bookmarksRepository.deleteById(markID);
    }
*/


}

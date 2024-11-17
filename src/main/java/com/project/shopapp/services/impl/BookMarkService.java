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



 @Override
    public List<BookmarkDTO> findBookmarksByBookId(Long bookID, String username)
    {
        // Tạo mới bookmark với sách và số trang
        Optional<Book> optionalBook = bookRepository.findById(bookID);
        if (!optionalBook.isPresent()) {
            throw new RuntimeException("Không tìm thấy Book với ID: " + bookID);
        }


        // Lấy danh sách bookmark theo sách (id)

        return bookmarksRepository.findByBook_BookID(bookID)
                .stream()
                .map(bookMark -> {
                    BookmarkDTO dto = new BookmarkDTO();

                    // Gán giá trị cho rating
                    dto.setPageNumber(bookMark.getPageNumber());

                    // Sử dụng trực tiếp Set<String> từ review.getComments()


                    // Lấy username từ đối tượng User liên kết với Review
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    dto.setCreateDate(bookMark.getCreateDate().format(formatter));

                    dto.setUsername(bookMark.getUser().getUsername());


                    // Gán thêm thuộc tính evaluate nếu có


                    return dto;
                })
                .collect(Collectors.toList());

    }

    @Override
    public ResponseEntity<?> deleteBookmarkById(Long bookID, User user) {
        // Tìm danh sách bookmark theo Book ID
        List<BookMark> bookmarks = bookmarksRepository.findByBook_BookID(bookID);

        // Kiểm tra xem danh sách có rỗng không
        if (bookmarks.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy Bookmark với Book ID: " + bookID, HttpStatus.BAD_REQUEST);
        }

        // Tìm kiếm và xóa bookmark thuộc về người dùng hiện tại
        for (BookMark bookmark : bookmarks) {
            if (bookmark.getUser().getId().equals(user.getId())) {
                // Xóa bookmark
                bookmarksRepository.delete(bookmark);
                return ResponseEntity.ok("Bookmark đã được xóa thành công.");
            }
        }

        // Nếu không tìm thấy bookmark của user
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền xóa Bookmark này.");
    }





}

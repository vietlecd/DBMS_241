    package com.project.shopapp.controllers;

    import com.project.shopapp.DTO.BookDTO;
    import com.project.shopapp.customexceptions.DataNotFoundException;
    import com.project.shopapp.customexceptions.InvalidParamException;
    import com.project.shopapp.helpers.AuthenticationHelper;
    import com.project.shopapp.models.User;
    import com.project.shopapp.responses.BookAuthorResponse;
    import com.project.shopapp.services.IBookService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.Authentication;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import javax.xml.crypto.Data;
    import java.io.IOException;
    import java.security.GeneralSecurityException;
    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("${api.prefix}")
    public class BookController{

        @Autowired
        private IBookService bookService;
        @Autowired
        private AuthenticationHelper authenticationHelper;

        @GetMapping("/getViewBook")
        public ResponseEntity<?> getViewBook(@RequestParam Integer bookId) {
            try {
                Integer res = bookService.count_view_book(bookId);
                return ResponseEntity.ok(res);
            } catch (DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @GetMapping("/readingBook")
        public ResponseEntity<?> readingBook (@RequestParam Integer bookId) {
            try {
                bookService.readingBook(bookId);
                return ResponseEntity.ok("Reading Recorded");
            } catch (DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @GetMapping("/bookByCategory")
        public ResponseEntity<?> getBook (
                @RequestParam String params) {
            try {
                List<BookAuthorResponse> result = bookService.findByCategory(params);
                return ResponseEntity.ok(result);
            } catch (DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @GetMapping("/bookByAuthor")
        public ResponseEntity<?> searchBookByAuthor (
                @RequestParam String authorName) {
            try {
                return bookService.getBooksByAuthor(authorName);
            } catch (DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @PostMapping(value = "/createBook", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<?> createBook(
                @ModelAttribute BookDTO bookDTO,
                Authentication authentication,
                @RequestPart("pdf") MultipartFile pdf,
                @RequestPart("image") MultipartFile image
        ) {
            try {
                User user = authenticationHelper.getUser(authentication);
                return bookService.createBook(bookDTO, pdf, image, user);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
            }
        }

        @DeleteMapping("/deleteBook")
        public String deleteBookByTitle(@RequestParam Integer bookID) {

            boolean deleted = bookService.deleteBookBybookID(bookID);
            return deleted ? "Book with title '" + bookID + "' was deleted successfully." : "Book with title '" + bookID+ "' not found.";
        }

        @PostMapping("/acceptBook")
        public String acceptBookRequestCheck(@RequestParam Integer bookID) {
            boolean requestAccepted = bookService.acceptBookRequestCheck(bookID);
            return requestAccepted ? "Book request for title '" + bookID + "' accepted." : "Book request for title '" + bookID + "' not found or already accepted.";
        }

        @PostMapping("/denyBook")
        public String denyBookRequestCheck(@RequestParam Integer bookID) {
            boolean requestDenied = bookService.denyBookRequestCheck(bookID);
            return requestDenied ? "Book request for title '" +  bookID + "' denied." : "Book request for title '" + bookID + "' not found or already denied.";
        }

        @GetMapping("/findBookBought")
        public ResponseEntity<?> findBookBought(Authentication authentication) {
            try {
                String username = authenticationHelper.getUsername(authentication);
                return bookService.getBookBought(username);
            } catch (DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @GetMapping("/findBookWritten")
        public ResponseEntity<?> findBookWritten(Authentication authentication) {
            try {
                String username = authenticationHelper.getUsername(authentication);
                return bookService.getBookWritten(username);
            } catch (DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @GetMapping("/countBookWritten")
        public ResponseEntity<?> countBookWritten(Authentication authentication) {
            try {
                String username = authenticationHelper.getUsername(authentication);
                return bookService.countBookWritten(username);
            } catch (DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @GetMapping("/bookFree")
        public ResponseEntity<?> bookFree() {
            try {
                return ResponseEntity.ok(bookService.getFreeBook());
            } catch (DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @GetMapping("/bookRecommend")
        public ResponseEntity<?> bookRecommend() {
            try {
                return ResponseEntity.ok(bookService.getRecommendBook());
            } catch (DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }

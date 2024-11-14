    package com.project.shopapp.controllers;

    import com.project.shopapp.DTO.BookDTO;
    import com.project.shopapp.responses.BookProjection;
    import com.project.shopapp.services.IBookService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("${api.prefix}")
    public class BookController {

        @Autowired
        private IBookService bookService;

        @GetMapping("/book")
        public List<BookDTO> getBook(
                @RequestParam Map<String, Object> params) {

            // Gọi đến service để lấy danh sách BookDTO dựa trên tham số truyền vào
            List<BookDTO> result = bookService.findAll(params);
            return result;
        }

        @PostMapping("/createbook")
        public String createBook(@RequestBody BookDTO bookDTO) {
            // Gọi đến service để tạo mới một Book
            BookDTO createdBook = bookService.createBook(bookDTO);
            return createdBook != null ? "Book with title '" + createdBook.getTitle() + "' during the processing and returning of results when available" : "Failed to send request check the book.";
        }

        @DeleteMapping("/api/deletebook")
        public String deleteBookByTitle(@RequestParam Long bookID) {

            // Gọi đến service để xóa sách theo title
            boolean deleted = bookService.deleteBookBybookID(bookID);
            return deleted ? "Book with title '" + bookID + "' was deleted successfully." : "Book with title '" + bookID+ "' not found.";
        }




        @PostMapping("/api/acceptbookrequestcheck")
        public String acceptBookRequestCheck(@RequestParam String title) {
            boolean requestAccepted = bookService.acceptBookRequestCheck(title.trim());
            return requestAccepted ? "Book request for title '" + title + "' accepted." : "Book request for title '" + title + "' not found or already accepted.";
        }

       /* @GetMapping("/api/pendingbookrequests")
        public List<BookDTO> getBooksWithPendingRequest() {
            return bookService.getBooksWithPendingRequest();
        }*/
        @PostMapping("/api/denybookrequestcheck")
        public String denyBookRequestCheck(@RequestParam String title) {
            boolean requestDenied = bookService.denyBookRequestCheck(title.trim());
            return requestDenied ? "Book request for title '" + title + "' denied." : "Book request for title '" + title + "' not found or already denied.";
        }

        @GetMapping("/findBookAuthor/{authorName}")
        public List<BookProjection> findBookAuthor(@PathVariable String authorName) {
            return bookService.getBooksByAuthor(authorName);
        }
    }

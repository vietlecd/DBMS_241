    package com.project.shopapp.controllers;

    import com.project.shopapp.DTO.BookDTO;
    import com.project.shopapp.services.IBookService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Map;

    @RestController
    public class BookController {

        @Autowired
        private IBookService bookService;

        @GetMapping("/api/book")
        public List<BookDTO> getBook(
                @RequestParam Map<String, Object> params) {

            // Gọi đến service để lấy danh sách BookDTO dựa trên tham số truyền vào
            List<BookDTO> result = bookService.findAll(params);
            return result;
        }
        @PostMapping("/api/createbook")
        public BookDTO createBook(
                @RequestBody BookDTO bookDTO) {
            // Gọi đến service để tạo mới một Book
            return bookService.createBook(bookDTO);
        }


        @DeleteMapping("/api/deletebook")
        public String deleteBookByTitle(@RequestParam String title) {
            // Gọi đến service để xóa sách theo title
            boolean deleted = bookService.deleteBookByTitle(title);
            return deleted ? "Book with title '" + title + "' was deleted successfully." : "Book with title '" + title + "' not found.";
        }


        @PostMapping("/api/sendbookrequestcheck")
        public String sendBookRequestCheck(@RequestParam String title) {
            System.out.println("Received title: '" + title + "'");
            boolean requestSent = bookService.sendBookRequestCheck(title);
            return requestSent ? "Book request for title '" + title + "' sent successfully." : "Book request for title '" + title + "' failed.";
        }

        @PostMapping("/api/acceptbookrequestcheck")
        public String acceptBookRequestCheck(@RequestParam String title) {
            boolean requestAccepted = bookService.acceptBookRequestCheck(title.trim());
            return requestAccepted ? "Book request for title '" + title + "' accepted." : "Book request for title '" + title + "' not found or already accepted.";
        }

        @GetMapping("/api/pendingbookrequests")
        public List<BookDTO> getBooksWithPendingRequest() {
            return bookService.getBooksWithPendingRequest();
        }
        @PostMapping("/api/denybookrequestcheck")
        public String denyBookRequestCheck(@RequestParam String title) {
            boolean requestDenied = bookService.denyBookRequestCheck(title.trim());
            return requestDenied ? "Book request for title '" + title + "' denied." : "Book request for title '" + title + "' not found or already denied.";
        }
    }

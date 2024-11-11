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
    }

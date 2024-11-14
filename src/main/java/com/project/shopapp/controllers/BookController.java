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
        public BookDTO createBook(@RequestBody BookDTO bookDTO) {
            // Gọi đến service để tạo mới một Book
            return bookService.createBook(bookDTO);
        }



        @DeleteMapping("/deletebook")
        public String deleteBookByTitle(@RequestParam Long bookID) {

            // Gọi đến service để xóa sách theo title
            boolean deleted = bookService.deleteBookBybookID(bookID);
            return deleted ? "Book with title '" + bookID + "' was deleted successfully." : "Book with title '" + bookID+ "' not found.";
        }




        @PostMapping("/acceptbookrequestcheck")
        public String acceptBookRequestCheck(@RequestParam Long bookID) {
            boolean requestAccepted = bookService.acceptBookRequestCheck(bookID);
            return requestAccepted ? "Book request for title '" + bookID + "' accepted." : "Book request for title '" + bookID + "' not found or already accepted.";
        }


        @PostMapping("/denybookrequestcheck")
        public String denyBookRequestCheck(@RequestParam Long bookID) {
            boolean requestDenied = bookService.denyBookRequestCheck(bookID);
            return requestDenied ? "Book request for title '" +  bookID + "' denied." : "Book request for title '" + bookID + "' not found or already denied.";
        }

        @GetMapping("/findBookAuthor/{authorName}")
        public List<BookProjection> findBookAuthor(@PathVariable String authorName) {
            return bookService.getBooksByAuthor(authorName);
        }
    }

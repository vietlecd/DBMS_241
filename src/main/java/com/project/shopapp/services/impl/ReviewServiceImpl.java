package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.ReviewDTO;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Review;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.repositories.ReviewRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IReviewService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<?> addReviewToBook(Long bookID, ReviewDTO reviewDTO, User user) {
        // Tìm đối tượng Book theo ID
        Optional<Book> optionalBook = bookRepository.findById(bookID);
        if (!optionalBook.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy Book với ID: " + bookID, HttpStatus.BAD_REQUEST);
        }
        Book book = optionalBook.get();

        // Tìm User dựa trên username trong ReviewDTO
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Không tìm thấy User với username: ");
        }

        String comment = reviewDTO.getComment();
        // Kiểm tra xem comment đã tồn tại trong hệ thống chưa
        //List<Review> existingReviews = reviewRepository.findByUsername(optionalUser.get().getUsername());

//        Review review;

        // Nếu chưa tồn tại, tạo mới một Review
        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(comment);
        review.setEvaluate(reviewDTO.getEvaluate());
        review.setBook(book);
        review.setUser(user); // Gán User cho Review

        // Lưu review mới vào cơ sở dữ liệu
        reviewRepository.save(review);

        // Thêm review vào tập hợp comments của Book
        book.getComments().add(review);


        // Tạo đối tượng DTO để trả về
        ReviewDTO savedReviewDTO = new ReviewDTO();
        savedReviewDTO.setRating(reviewDTO.getRating());
        savedReviewDTO.setUsername(user.getUsername());
        // Thêm username vào DTO trả về
        savedReviewDTO.setEvaluate(reviewDTO.getEvaluate());
        savedReviewDTO.setComment(comment);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedReviewDTO);
    }

    @Override
    public ResponseEntity<?> findReviewsByBookId(Long bookID, String username) {
        try {
            // Kiểm tra sự tồn tại của Book theo bookID
            Optional<Book> optionalBook = bookRepository.findById(bookID);
            if (!optionalBook.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy Book với ID: " + bookID);
            }

            // Tìm tất cả các Review dựa trên bookID và chuyển đổi chúng thành ReviewDTO
            List<ReviewDTO> reviewDTOList = reviewRepository.findByBook_BookID(bookID)
                    .stream()
                    .map(review -> {
                        ReviewDTO dto = new ReviewDTO();

                        // Gán giá trị cho rating
                        dto.setRating(review.getRating());

                        // Gán comment từ review
                        dto.setComment(review.getComment());

                        // Lấy username từ đối tượng User liên kết với Review
                        dto.setUsername(review.getUser().getUsername());

                        // Gán thêm thuộc tính evaluate nếu có
                        dto.setEvaluate(review.getEvaluate()); // Giả sử review có thuộc tính evaluate

                        return dto;
                    })
                    .collect(Collectors.toList());

            // Trả về danh sách ReviewDTO trong ResponseEntity
            return ResponseEntity.ok(reviewDTOList);
        } catch (Exception e) {
            // Trả về lỗi nếu xảy ra ngoại lệ
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateReview(Long reviewID, ReviewDTO reviewDTO, User user) {
        try {
            // Kiểm tra sự tồn tại của Review
            Optional<Review> optionalReview = reviewRepository.findById(reviewID);
            if (optionalReview.isPresent()) {
                Review review = optionalReview.get();

                // Cập nhật giá trị từ ReviewDTO
                review.setRating(reviewDTO.getRating());
                review.setComment(reviewDTO.getComment());
                review.setEvaluate(reviewDTO.getEvaluate());
                review.setUser(user);

                // Lưu lại thay đổi vào repository
                reviewRepository.save(review);

                // Chuyển đổi Review đã cập nhật thành ReviewDTO để trả về
                ReviewDTO updatedReviewDTO = new ReviewDTO();
                updatedReviewDTO.setRating(review.getRating());
                updatedReviewDTO.setComment(review.getComment());
                updatedReviewDTO.setUsername(review.getUser().getUsername());
                updatedReviewDTO.setEvaluate(review.getEvaluate());

                // Trả về ResponseEntity chứa ReviewDTO sau khi cập nhật thành công
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedReviewDTO);
            } else {
                // Xử lý nếu Review không tồn tại
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Review not found with ID: " + reviewID);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }


}

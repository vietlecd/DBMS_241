package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.ReviewDTO;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Review;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.repositories.ReviewRepository;
import com.project.shopapp.services.IReviewService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    public ReviewDTO addReviewToBook(Long bookID, ReviewDTO reviewDTO) {
        // Khởi tạo đối tượng Review mới từ dữ liệu trong DTO
        Review review = new Review();
        review.setRating(reviewDTO.getRating());

        // Lấy Book từ repository dựa trên bookID
        Optional<Book> bookOptional = bookRepository.findById(bookID);
        if (bookOptional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sách");
        }
        Book book = bookOptional.get();

        // Khởi tạo một tập hợp để lưu các review hiện có
        Set<Review> reviews = new HashSet<>();

        // Duyệt qua danh sách tên người dùng trong DTO và thêm các review tương ứng
        for (String username : reviewDTO.getComments()) {
            List<Review> existingReviews = reviewRepository.findReviewByFullname(username);
            if (!existingReviews.isEmpty()) {
                Review existingReview = existingReviews.get(0);
                reviews.add(existingReview);
            }
        }

        // Thêm review mới vào tập hợp reviews
        reviews.add(review);
        review.setBook(book); // Liên kết review với sách
        book.getReviews().add(review); // Thêm review vào tập hợp review của sách

        // Lưu review mới và cập nhật sách
        reviewRepository.save(review);
        bookRepository.save(book);

        // Chuyển review đã lưu sang DTO và trả về
        ReviewDTO savedReviewDTO = new ReviewDTO();
        savedReviewDTO.setComments(reviewDTO.getComments());
        savedReviewDTO.setRating(review.getRating());


        return savedReviewDTO;
    }
}

package com.project.shopapp.helpers;

import com.project.shopapp.models.Book;
import com.project.shopapp.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@AllArgsConstructor
public class BookViewHelper {
    private RedisTemplate<String, String> redisTemplate;
    private BookRepository bookRepository;

    public void incrementViewCountInRedis(Integer bookId) {
        String redisKey = "book::" + bookId + "::view_count";
        redisTemplate.opsForValue().increment(redisKey, 1);

        redisTemplate.expire(redisKey, Duration.ofHours(1));

    }

    public Integer getViewCountFromRedis(Integer bookId) {
        String redisKey = "book::" + bookId + "::view_count";
        String viewCount = redisTemplate.opsForValue().get(redisKey);
        if (viewCount != null) {
            System.out.print(viewCount);
            return Integer.parseInt(viewCount);
        } else {
            return bookRepository.count_book_view(bookId);
        }
    }

    public void deleteViewCountFromRedis(Integer bookId) {
        String redisKey = "book::" + bookId + "::view_count";
        redisTemplate.delete(redisKey);
    }

    @Scheduled(fixedRate = 300000)
    public void syncViewCountFromRedisToDatabase() {
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            Integer bookId = book.getBookID();
            Integer redisViewCount = getViewCountFromRedis(bookId);

            if (redisViewCount != null && redisViewCount > 0) {
                book.setView_count(redisViewCount);
                bookRepository.save(book);

                deleteViewCountFromRedis(bookId);
            }
        }
    }
}

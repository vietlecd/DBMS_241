package com.project.shopapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRedis implements Serializable {
    @Id
    private Integer bookID;
    private String title;
    private String description;
    private String uploaderName;
    private String coverImage;
}

package com.project.shopapp.repositories.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity

@Table(name = "book")
public class BookEntity {

    @Id
    @Column(name = "bookID")  // Tên cột trong DB
    private Long bookID;

    @Column(name = "title")  // Tên cột trong DB
    private String title;

    @Column(name = "description")  // Đặt tên cột trùng với DB
    private String description;

    @Column(name = "coverimage")  // Đặt tên cột trùng với DB
    private String coverimage;

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }

    public Integer getPublishyear() {
        return publishyear;
    }

    public void setPublishyear(Integer publishyear) {
        this.publishyear = publishyear;
    }

    @Column(name = "publishyear")  // Đặt tên cột trùng với DB
    private Integer publishyear;

}

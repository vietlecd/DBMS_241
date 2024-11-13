package com.project.shopapp.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookID")
    private Long bookID;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "coverimage")
    private String coverimage;

    @Column(name = "price")
    private String price;

    @Column(name = "publishyear")
    private Integer publishyear;

    @Column(name = "status")
    private String status;

    @ManyToMany
    @JoinTable(
            name = "cate", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "bookID", referencedColumnName = "bookID"), // Tham chiếu đến bookID của BookEntity
            inverseJoinColumns = @JoinColumn(name = "cateID", referencedColumnName = "cateID") // Tham chiếu đến cateID của CategoryEntity
    )
    private Set<Category> categories;

    // Getters và setters
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getPublishyear() {
        return publishyear;
    }

    public void setPublishyear(Integer publishyear) {
        this.publishyear = publishyear;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

  /*  public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }*/

  /*  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

*/

}
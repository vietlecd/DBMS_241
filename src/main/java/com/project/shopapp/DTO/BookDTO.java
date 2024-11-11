package com.project.shopapp.DTO;

public class BookDTO {
    private Long bookID;
    private String bTitle;

    private String coverImage;

    private String catedescription;
    private String price;

    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatedescription() {
        return catedescription;
    }

    public void setCatedescription(String catedescription) {
        this.catedescription = catedescription;
    }

    private int publishYear;

    // Getter và Setter cho bookID
    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }

    // Getter và Setter cho bTitle
    public String getbTitle() {
        return bTitle;
    }

    public void setbTitle(String bTitle) {
        this.bTitle = bTitle;
    }

    // Getter và Setter cho bDescription




    // Getter và Setter cho coverImage
    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    // Getter và Setter cho categoryName



    // Getter và Setter cho price
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    // Getter và Setter cho publishYear
    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }
}

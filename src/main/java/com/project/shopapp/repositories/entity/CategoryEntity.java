

package com.project.shopapp.repositories.entity;

public class CategoryEntity {
    private int cateID;
    private String name;
    private String cateDescription;

    // Getter và Setter cho cateID
    public int getCateID() {
        return cateID;
    }

    public void setCateID(int cateID) {
        this.cateID = cateID;
    }

    // Getter và Setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho cateDescription
    public String getCateDescription() {
        return cateDescription;
    }

    public void setCateDescription(String cateDescription) {
        this.cateDescription = cateDescription;
    }

}

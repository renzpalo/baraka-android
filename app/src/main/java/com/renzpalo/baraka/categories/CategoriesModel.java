package com.renzpalo.baraka.categories;

public class CategoriesModel {


    public String catId, catName, catImage;

    public CategoriesModel(String catId, String catName, String catImage) {
        this.catId = catId;
        this.catName = catName;
        this.catImage = catImage;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }
}

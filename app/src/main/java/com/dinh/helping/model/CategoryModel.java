package com.dinh.helping.model;

public class CategoryModel  {


    /**
     * categoryId : 1
     * categoryName : Iphone
     * categoryImage : https://mobishops.herokuapp.com:443/images/imageCategory/iphone.png
     * promotionId : 1
     */

    private int categoryId;
    private String categoryName;
    private String categoryImage;
    private int promotionId;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }
}

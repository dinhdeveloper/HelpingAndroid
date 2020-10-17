package com.dinh.helping.model;

public class PhotoModel {
    /**
     * image_id : 1
     * product_id : 1
     * product_photo : hoa.jpg
     */

    private String image_id;
    private String product_id;
    private String product_photo;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }
}

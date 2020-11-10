package com.dinh.helping.model;

import java.util.List;

public class ProductModel extends BaseResponseModel {

    /**
     * product_id : 1
     * category_id : 1
     * category_name : Đồ điện tử
     * product_name : Máy tính bảng
     * product_image : maytinh.jpg
     * price_sale : 120900
     * quantity : 1
     * description : iPad Mini 7.9 inch Wifi Cellular 64GB (2019) được Apple trang bị hiệu năng rất ấn tượng với con chip Apple A12 cùng RAM 3 GB và 64 GB bộ nhớ trong.
     * discount : 10
     * location : Bình Thạnh, Hồ Chí Minh
     * status : Y
     * product_photo : [{"image_id":"1","product_id":"1","product_photo":"hoa.jpg"},{"image_id":"2","product_id":"1","product_photo":"hoa.jpg"},{"image_id":"3","product_id":"1","product_photo":"heo.jpg"}]
     */

    private String product_id;
    private String category_id;
    private String category_name;
    private String product_name;
    private String phone_contact;
    private String product_image;
    private String price_sale;
    private String quantity;
    private String date_create;
    private String description;
    private String discount;
    private String location;
    private String status;
    private String city_id;
    private String district_id;
    private String ward_id;
    private String city_name;
    private String district_name;
    private String ward_name;
    private PhotoModel[] product_photo;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPhone_contact() {
        return phone_contact;
    }

    public void setPhone_contact(String phone_contact) {
        this.phone_contact = phone_contact;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getPrice_sale() {
        return price_sale;
    }

    public void setPrice_sale(String price_sale) {
        this.price_sale = price_sale;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PhotoModel[] getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(PhotoModel[] product_photo) {
        this.product_photo = product_photo;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }
}

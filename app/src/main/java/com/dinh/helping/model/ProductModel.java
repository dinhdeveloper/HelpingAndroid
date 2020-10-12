package com.dinh.helping.model;

public class ProductModel  {

    /**
     * productId : 1
     * categoryId : 1
     * productName : IPhone Xs Max 256GB
     * productPrice : 34000000
     * percentSale : 0
     * priceSale : 30000000
     * amount : 10
     * productImage : https://mobishops.herokuapp.com:443/images/imageProduct/iphone/iphone_1.png
     * status : 1
     * rate : 3.5
     */

    private int productId;
    private int categoryId;
    private String productName;
    private String productPrice;
    private String percentSale;
    private String priceSale;
    private String amount;
    private String productImage;
    private String status;
    private String rate;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getPercentSale() {
        return percentSale;
    }

    public void setPercentSale(String percentSale) {
        this.percentSale = percentSale;
    }

    public String getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(String priceSale) {
        this.priceSale = priceSale;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}

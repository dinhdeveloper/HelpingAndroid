package com.dinh.helping.model;

import com.canhdinh.lib.searchdialog.core.Searchable;

public class CategoryModel extends BaseResponseModel implements Searchable {


    /**
     * category_id : 1
     * category_name : Đồ điện tử
     * category_image : dientu.jpg
     */

    private String category_id;
    private String category_name;
    private String category_image;

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

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    @Override
    public String getTitle() {
        return category_name;
    }
}

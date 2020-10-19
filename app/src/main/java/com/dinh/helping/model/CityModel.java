package com.dinh.helping.model;

import com.canhdinh.lib.searchdialog.core.Searchable;

public class CityModel  extends BaseResponseModel implements Searchable {

    /**
     * city_id : 01
     * city_name : Thành phố Hà Nội
     * city_type : Thành phố Trung ương
     */

    private String city_id;
    private String city_name;
    private String city_type;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_type() {
        return city_type;
    }

    public void setCity_type(String city_type) {
        this.city_type = city_type;
    }

    @Override
    public String getTitle() {
        return city_name;
    }
}

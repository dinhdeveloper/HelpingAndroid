package com.dinh.helping.model;

import com.canhdinh.lib.searchdialog.core.Searchable;

public class DistrictModel extends BaseResponseModel implements Searchable {

    /**
     * district_id : 001
     * city_id : 01
     * district_name : Quận Ba Đình
     * district_type : Quận
     */

    private String district_id;
    private String city_id;
    private String district_name;
    private String district_type;

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getDistrict_type() {
        return district_type;
    }

    public void setDistrict_type(String district_type) {
        this.district_type = district_type;
    }

    @Override
    public String getTitle() {
        return district_name;
    }
}

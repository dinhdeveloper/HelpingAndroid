package com.dinh.helping.model;

import com.canhdinh.lib.searchdialog.core.Searchable;

public class WardModel extends BaseResponseModel implements Searchable {


    /**
     * ward_id : 00001
     * district_id : 001
     * ward_name : Phường Phúc Xá
     * ward_type : Phường
     */

    private String ward_id;
    private String district_id;
    private String ward_name;
    private String ward_type;

    @Override
    public String getTitle() {
        return ward_name;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }

    public String getWard_type() {
        return ward_type;
    }

    public void setWard_type(String ward_type) {
        this.ward_type = ward_type;
    }
}

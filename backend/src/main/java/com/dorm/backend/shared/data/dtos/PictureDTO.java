package com.dorm.backend.shared.data.dtos;

public class PictureDTO {
    private String  base64String;
    private String  name;
    private Integer pictureOrder;

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPictureOrder() {
        return pictureOrder;
    }

    public void setPictureOrder(Integer pictureOrder) {
        this.pictureOrder = pictureOrder;
    }
}

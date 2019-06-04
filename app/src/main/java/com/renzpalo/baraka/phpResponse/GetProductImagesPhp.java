package com.renzpalo.baraka.phpResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductImagesPhp {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("information")
    @Expose
    private Information information;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public class Image {

        @SerializedName("image_id")
        @Expose
        private String imageId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("image_seq")
        @Expose
        private String imageSeq;

        public String getImageId() {
            return imageId;
        }

        public void setImageId(String imageId) {
            this.imageId = imageId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImageSeq() {
            return imageSeq;
        }

        public void setImageSeq(String imageSeq) {
            this.imageSeq = imageSeq;
        }

    }

    public class Information {

        @SerializedName("images")
        @Expose
        private List<Image> images = null;

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }


    }

}

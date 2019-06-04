package com.renzpalo.baraka.productPreview;

public class ReviewModel {

    public String reviewId, reviewSummary, reviewFeedback, reviewUserName, reviewDate, reviewRating;

    public ReviewModel(String reviewId, String reviewSummary, String reviewFeedback, String reviewUserName, String reviewDate, String reviewRating) {
        this.reviewId = reviewId;
        this.reviewSummary = reviewSummary;
        this.reviewFeedback = reviewFeedback;
        this.reviewUserName = reviewUserName;
        this.reviewDate = reviewDate;
        this.reviewRating = reviewRating;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewSummary() {
        return reviewSummary;
    }

    public void setReviewSummary(String reviewSummary) {
        this.reviewSummary = reviewSummary;
    }

    public String getReviewFeedback() {
        return reviewFeedback;
    }

    public void setReviewFeedback(String reviewFeedback) {
        this.reviewFeedback = reviewFeedback;
    }

    public String getReviewUserName() {
        return reviewUserName;
    }

    public void setReviewUserName(String reviewUserName) {
        this.reviewUserName = reviewUserName;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(String reviewRating) {
        this.reviewRating = reviewRating;
    }
}

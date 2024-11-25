package com.embarkx.firstjobapp.review.dto;

public class ReviewRequestDTO {
    private String title;
    private String description;
    private Double rating;

    public ReviewRequestDTO() {
    }

    public ReviewRequestDTO(String title, String description, Double rating) {
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ReviewRequestDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }
}

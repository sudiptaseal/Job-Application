package com.embarkx.firstjobapp.company.dto;

import com.embarkx.firstjobapp.review.Review;

import java.util.List;

public class CompanyResponseDTO {
    private Long id;
    private String name;
    private String description;
    private List<Review> reviews;

    public CompanyResponseDTO() {
    }

    public CompanyResponseDTO(Long id, String name, String description, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "CompanyResponseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}

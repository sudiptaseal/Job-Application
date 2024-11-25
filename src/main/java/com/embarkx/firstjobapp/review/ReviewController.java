package com.embarkx.firstjobapp.review;

import com.embarkx.firstjobapp.company.Company;
import com.embarkx.firstjobapp.review.dto.ReviewRequestDTO;
import com.embarkx.firstjobapp.review.dto.ReviewResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews(@PathVariable Long companyId) {
        List<Review> reviews = reviewService.getAllReviews(companyId);
        List <ReviewResponseDTO> reviewResponseDTOs = new ArrayList<>();
        if(reviews != null && reviews.size() >0) {
            reviews.stream().forEach(review -> {
                ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
                reviewResponseDTO.setId(review.getId());
                reviewResponseDTO.setTitle(review.getTitle());
                reviewResponseDTO.setDescription(review.getDescription());
                reviewResponseDTO.setRating(review.getRating());

                reviewResponseDTOs.add(reviewResponseDTO);
            });
        }
        return new ResponseEntity<>(reviewResponseDTOs, HttpStatus.OK);
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> addReview(@PathVariable Long companyId,
                                            @RequestBody ReviewRequestDTO reviewRequestDTO) {
        if(reviewRequestDTO != null) {
            Review review = new Review();
            review.setTitle(reviewRequestDTO.getTitle());
            review.setDescription(reviewRequestDTO.getDescription());
            review.setRating(reviewRequestDTO.getRating());

            boolean isReviewSaved = reviewService.addReview(companyId,review);
            if(isReviewSaved)
                return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Review not saved",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> getReview(@PathVariable Long companyId,
                                            @PathVariable Long reviewId) {
        Review review = reviewService.getReview(companyId,reviewId);
        if(review != null) {
            ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
            reviewResponseDTO.setId(review.getId());
            reviewResponseDTO.setTitle(review.getTitle());
            reviewResponseDTO.setDescription(review.getDescription());
            reviewResponseDTO.setRating(review.getRating());
            return new ResponseEntity<>(reviewResponseDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId,
                                               @RequestBody ReviewRequestDTO reviewRequestDTO) {
        if(reviewRequestDTO != null) {
            Review review = new Review();
            review.setTitle(reviewRequestDTO.getTitle());
            review.setDescription(reviewRequestDTO.getDescription());
            review.setRating(reviewRequestDTO.getRating());
            Company company = new Company();
            company.setId(companyId);
            review.setCompany(company);

            boolean isReviewUpdated = reviewService.updateReview(companyId,reviewId,review);
            if(isReviewUpdated) {
                return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Review not updated", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Review not updated", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId) {
        boolean isReviewDeleted = reviewService.deleteReview(companyId,reviewId);
        if(isReviewDeleted) {
            return new ResponseEntity<>("Review Deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review not deleted", HttpStatus.NOT_FOUND);
    }
}

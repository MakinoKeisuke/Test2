package com.example.samuraitravel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.form.ReviewsRegisterForm;
import com.example.samuraitravel.repository.ReviewRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	
	
	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional
	public void createReview(ReviewsRegisterForm reviewsForm) {
		// TODO 自動生成されたメソッド・スタブ
		Review review = new Review();
		
		review.setScore(reviewsForm.getScore());
		review.setComments(reviewsForm.getComment());
		
		reviewRepository.save(review);
	}
	
	@Transactional
	public void update(ReviewsRegisterForm reviewsForm) {
		Review  review =  new Review();
		
		review.setScore(reviewsForm.getScore());
		review.setComments(reviewsForm.getComment());
		
		reviewRepository.save(review);
	}
	
}

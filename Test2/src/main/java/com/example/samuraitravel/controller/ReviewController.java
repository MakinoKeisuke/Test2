package com.example.samuraitravel.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.form.ReviewsRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.service.ReviewService;

@Controller
public class ReviewController {
	private final HouseRepository houseRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	
	public ReviewController(HouseRepository houseRepository, ReviewRepository reviewRepository, ReviewService reviewService) {
		this.houseRepository = houseRepository;
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
	}
	
	@GetMapping("houses/{id}/review")
	public String review(@PathVariable(name = "houseId") Integer id, @PageableDefault(page=0, size=10,sort="id", direction=Direction.ASC) Pageable pageable,Model model) {
		Page<Review> page = reviewRepository.findAllByOrderByCreatedAtDesc(pageable);
		
		House house = houseRepository.getReferenceById(id);
		List<Review> review =  reviewRepository.findByHouseId(id);
		
		if(review == null) {
	        review = new ArrayList<>(); // 空のリストを初期化
	    }
		model.addAttribute("page", page);
		model.addAttribute("house", house);
		model.addAttribute("review", review);
		return "houses/review";
	}
	@GetMapping("houses/{id}/registerReview")
	public String register(@PathVariable(name = "id") Integer id,Model model) {
		House house = new House();
		//House house = houseRepository.getReferenceById(id);		
		model.addAttribute("house", house);
		model.addAttribute("reviewsForm", new ReviewsRegisterForm());
		return "houses/register";
	}
	@PostMapping("houses/createReview")
	public String create(@ModelAttribute @Validated ReviewsRegisterForm reviewsForm, BindingResult bindingResult, RedirectAttributes RedirectAttributes, Model model) {
		if(bindingResult.hasErrors()) {
			return "houses/index";
		}
		reviewService.createReview(reviewsForm);
		
		RedirectAttributes.addFlashAttribute("successMessage", "評価を登録しました");
	    
	    return "redirect:/houses";
	}
	
	
	@PostMapping("houses/{id}/updateReview")
	public String update(@PathVariable Integer id,@ModelAttribute @Validated ReviewsRegisterForm reviewsForm, BindingResult bindingresult, RedirectAttributes redirectAttributes) {
		if(bindingresult.hasErrors()) {
			return "houses/review";
		}
		reviewService.createReview(reviewsForm);
		
		redirectAttributes.addFlashAttribute("successMessage", "評価を更新しました");
	    
	    return "redirect:/houses";
	}
	@PostMapping("houses/{id}/deleteReview")
	public String delete(@PathVariable Integer id,@ModelAttribute @Validated ReviewsRegisterForm reviewsForm, BindingResult bindingresult, RedirectAttributes redirectAttributes) {
		if(bindingresult.hasErrors()) {
			return "houses/review";
		}
		reviewService.createReview(reviewsForm);
		
		redirectAttributes.addFlashAttribute("successMessage", "評価を削除しました");
	    
	    return "redirect:/houses";
	}
	
}

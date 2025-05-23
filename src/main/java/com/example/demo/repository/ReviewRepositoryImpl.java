package com.example.demo.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Review;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

	private final JdbcTemplate jdbcTemplate;
	
	@Override
	public void add(Review review) {
		
		System.out.println("----登録----");
		System.out.println(review);
		
		String sql = 
				" INSERT INTO t_review " + 
				" (restaurant_id, user_id, visit_date, rating, comment)" + 
				" VALUES (?, ?, ?, ?, ?)";
		
		jdbcTemplate.update( sql,
				review.getRestaurantId(),review.getUserId(),review.getVisitDate(),
				review.getRating(),review.getComment()
				);

	}

	@Override
	public List<Review> selectByRestaurantId(int restaurantId) {
		
		String sql = 
				"SELECT review_id, restaurant_id, user_id, visit_date, rating, comment " +
				"FROM t_review WHERE restaurant_id = ? ORDER BY visit_date DESC, review_id ASC ";
		
		System.out.println(sql);
		
		List<Map<String, Object>> list 
								= jdbcTemplate.queryForList(sql, restaurantId);
		
		System.out.println(list);
		
		List<Review> result = new ArrayList<Review>();
		for(Map<String, Object> one : list) {
			Review review = new Review();
			review.setReviewId((int)one.get("review_id"));
			review.setRestaurantId((int)one.get("restaurant_id"));
			review.setUserId((String)one.get("user_id"));
			review.setVisitDate((Date)one.get("visit_date"));
			review.setRating((int)one.get("rating"));
			review.setComment((String)one.get("comment"));
			result.add(review);
		}		
		return result;
	}
}

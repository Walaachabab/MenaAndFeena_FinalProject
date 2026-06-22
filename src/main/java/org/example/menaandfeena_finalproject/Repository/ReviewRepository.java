package org.example.menaandfeena_finalproject.Repository;


import org.example.menaandfeena_finalproject.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review findReviewById(Integer id);
    List<Review> findByEvent_Id(Integer eventId);
    List<Review> findByInitiative_Id(Integer initiativeId);
    boolean existsByUserIdAndEventId(Integer userId, Integer eventId);
    boolean existsByUserIdAndInitiativeId(Integer userId, Integer initiativeId);
    boolean existsByOrderItemId(Integer orderItemId);
    List<Review> findByEvent_IdOrderByCreatedAtDesc(Integer eventId);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.event.id = :eventId")
    Double getAverageRatingByEventId(@Param("eventId") Integer eventId);
    Integer countByEvent_Id(Integer eventId); // تحسب جميع التقييمات
    Integer countByEvent_IdAndRatingGreaterThanEqual(Integer eventId, Integer rating);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.initiative.id = :initiativeId")
    Double getAverageRatingByInitiativeId(@Param("initiativeId") Integer initiativeId);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.targetUser.id = :sellerId")
    Double getAverageRatingByTargetUserId(@Param("sellerId") Integer sellerId);
    Integer countByInitiative_Id(Integer initiativeId);
    Integer countByInitiative_IdAndRatingGreaterThanEqual(Integer initiativeId, Integer rating);

    List<Review> findByUserId(Integer userId);

    List<Review> findByTargetUserId(Integer userId);

    Integer countByUser_Neighborhood_Id(Integer neighborhoodId);
    List<Review> findByUser_Neighborhood_Id(Integer neighborhoodId);
}

package org.example.menaandfeena_finalproject.Repository;

import org.example.menaandfeena_finalproject.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    OrderItem findOrderItemById(Integer id);

    List<OrderItem> findOrderItemsByOrdersId(Integer orderId);

    @Query("""
            SELECT COUNT(oi)
            FROM OrderItem oi
            WHERE oi.marketPlaceItem.user.id = :sellerId
            AND oi.orders.status IN ('PAID', 'COMPLETED')
            """)
    Long countCompletedPurchasesBySellerId(@Param("sellerId") Integer sellerId);
}

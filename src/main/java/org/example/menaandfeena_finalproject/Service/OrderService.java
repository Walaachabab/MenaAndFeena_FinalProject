package org.example.menaandfeena_finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiException;
import org.example.menaandfeena_finalproject.DTO.In.OrderInDTO;
import org.example.menaandfeena_finalproject.DTO.Out.OrderOutDTO;
import org.example.menaandfeena_finalproject.Model.Order;
import org.example.menaandfeena_finalproject.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrderOutDTO> getAllOrders() {
        List<OrderOutDTO> orderOutDTOS = new ArrayList<>();

        for (Order order : orderRepository.findAll()) {
            orderOutDTOS.add(toOutDTO(order));
        }

        return orderOutDTOS;
    }

    public void addOrder(OrderInDTO orderInDTO) {
        Order order = new Order();
        order.setType(orderInDTO.getType());
        order.setStatus(orderInDTO.getStatus());
        order.setTotalAmount(orderInDTO.getTotalAmount());
        order.setStartDate(orderInDTO.getStartDate());
        order.setEndDate(orderInDTO.getEndDate());

        orderRepository.save(order);
    }

    public void updateOrder(Integer id, OrderInDTO orderInDTO) {
        Order oldOrder = orderRepository.findOrderById(id);

        if (oldOrder == null) {
            throw new ApiException("Order not found");
        }

        oldOrder.setType(orderInDTO.getType());
        oldOrder.setStatus(orderInDTO.getStatus());
        oldOrder.setTotalAmount(orderInDTO.getTotalAmount());
        oldOrder.setStartDate(orderInDTO.getStartDate());
        oldOrder.setEndDate(orderInDTO.getEndDate());

        orderRepository.save(oldOrder);
    }

    public void deleteOrder(Integer id) {
        Order order = orderRepository.findOrderById(id);

        if (order == null) {
            throw new ApiException("Order not found");
        }

        orderRepository.delete(order);
    }

    private OrderOutDTO toOutDTO(Order order) {
        return new OrderOutDTO(order.getId(), order.getType(), order.getStatus(), order.getTotalAmount(), order.getStartDate(), order.getEndDate());
    }
}

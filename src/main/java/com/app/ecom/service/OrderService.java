package com.app.ecom.service;

import com.app.ecom.dto.OrderItemDTO;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.model.*;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {


private final CartService cartService;
private final UserRepository userRepository;
private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {

        // validate for cart items if available
        List<CartItem> cartItems =cartService.getCartItems(userId);
        if(cartItems.isEmpty()){
            return Optional.empty();
        }
        // valid user id
       Optional<User> userOptional =  userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty()){
            return Optional.empty();
        }

        User user = userOptional.get();

        // calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        // create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotal(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItem(
                        null,
                        cartItem.getProduct(),
                        cartItem.getQuantity(),
                        cartItem.getPrice(),
                        order
                )).toList();
        order.setItems(orderItems);
        Order saveOrder = orderRepository.save(order);

        // clear the cart
        cartService.clearCart(userId);


        return Optional.of(mapToOrderResponse(saveOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotal(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProduct().getId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        )).toList(), order.getCreatedAt()
        );
    }
}

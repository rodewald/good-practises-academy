package pl.praktycznajava.module2.encapsulation.service;

import pl.praktycznajava.module2.encapsulation.model.*;
import pl.praktycznajava.module2.encapsulation.repository.OrderRepository;


public class OrderService {

    OrderRepository orderRepository;


    public void changeAddress(String orderId, Address deliveryAddress) {
        Order order = orderRepository.findBy(orderId);
        order.updateDeliveryAddress(deliveryAddress);
        orderRepository.save(order);
    }

    public void changeDeliveryType(String orderId, DeliveryType deliveryType) {
        Order order = orderRepository.findBy(orderId);
        order.updateDeliveryType(deliveryType);
        orderRepository.save(order);
    }

    public void addProduct(String orderId, Product product, int quantity) {
        Order order = orderRepository.findBy(orderId);

        OrderItem newItem = OrderItem.of(product, quantity);
        order.addProduct(newItem);

        orderRepository.save(order);
    }

    public void completeOrder(String orderId) {
        Order order = orderRepository.findBy(orderId);
        order.completeOrder();
        orderRepository.save(order);
    }


}

package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class Order {

   List<OrderItem> items;
   DeliveryType deliveryType;
   OrderStatus status;
   Address deliveryAddress;
   BigDecimal totalAmount;
   BigDecimal discountAmount;
   BigDecimal deliveryCost;

   public void updateDeliveryAddress(Address deliveryAddress) {
      this.deliveryAddress = deliveryAddress;
      calculateDeliveryCost();
   }

   public void updateDeliveryType(DeliveryType deliveryType) {
      this.deliveryType = deliveryType;
      calculateDeliveryCost();
   }

   public void completeOrder() {
      for (OrderItem item : items) {
         Product product = item.getProduct();
         int quantity = item.getQuantity();
         if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException(product, quantity);
         }
         product.setStockQuantity(product.getStockQuantity() - quantity);
      }
      status = OrderStatus.COMPLETED;
   }

   public void addProduct(OrderItem newItem) {
      items.add(newItem);

      BigDecimal itemAmount = newItem.getProduct().getPrice().multiply(BigDecimal.valueOf(newItem.getQuantity()));

      totalAmount = totalAmount.add(itemAmount);
      discountAmount = calculateDiscount(totalAmount);

      calculateDeliveryCost();
   }

   private BigDecimal calculateDiscount(BigDecimal totalAmount) {
      BigDecimal discount = BigDecimal.ZERO;
      if (totalAmount.compareTo(BigDecimal.valueOf(500)) > 0) {
         discount = totalAmount.multiply(BigDecimal.valueOf(0.2));
      } else if (totalAmount.compareTo(BigDecimal.valueOf(50)) > 0) {
         discount = totalAmount.multiply(BigDecimal.valueOf(0.05));
      }
      return discount;
   }

   private void calculateDeliveryCost() {
      double totalWeight = items.stream()
              .mapToDouble(item -> item.getProduct().getWeight() * item.getQuantity())
              .sum();

      double deliveryTypeCost = deliveryType == DeliveryType.EXPRESS ? 30 : 15;
      double shippingCost = totalWeight * 0.5 + deliveryTypeCost;
      if (deliveryAddress.getCountry().equals("Polska")) {
         shippingCost += 80;
      }
      if (totalWeight > 100) {
         shippingCost *= 1.1;
      }
      deliveryCost = BigDecimal.valueOf(shippingCost);
   }
}
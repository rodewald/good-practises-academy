package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class OrderItem {

    Product product;
    int quantity;

    public double calculateTotalWeightProduct() {
        return product.getWeight() * quantity;
    }

    public BigDecimal OrderItemPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}

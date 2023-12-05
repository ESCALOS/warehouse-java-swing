/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nanoka.almacenrepuestos.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movement implements Comparable<Movement>{
    int id;
    Product product;
    int quantity;
    MovementType movementType; 
    BigDecimal price;
    LocalDateTime datetime;

    @Override
    public int compareTo(Movement o) {
        return this.product.getName().compareTo(o.product.getName());
    }
}

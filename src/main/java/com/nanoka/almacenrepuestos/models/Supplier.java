/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nanoka.almacenrepuestos.models;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Supplier implements Comparable<Supplier> {
    int id;
    String ruc;
    String name;
    String tel;
    String email;

    @Override
    public int compareTo(Supplier o) {
        return this.name.compareTo(o.getName());
    }
    
    public String toString() {
        return this.name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Supplier other = (Supplier) obj;
        return id == other.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

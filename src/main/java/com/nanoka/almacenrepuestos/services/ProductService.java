/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nanoka.almacenrepuestos.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nanoka.almacenrepuestos.models.Product;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlo
 */
public class ProductService {
    public List<Product> products = new ArrayList<>();

    private void loadData(List<Product> products) {
        products.clear();
        try (FileReader reader = new FileReader("products.json",StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            products.addAll(gson.fromJson(reader, new TypeToken<List<Product>>(){}.getType()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public List<Product> getProducts() {
        this.refreshData();
        return this.products;
    }
    
    public List<Product> search(String search) {
        List<Product> matching = new ArrayList<>();
        for(Product product : this.products){
            if(product.getName().toLowerCase().contains(search.toLowerCase())){
                matching.add(product);
            }
        }
        return matching;
    }
    /**
     * Guarda al proveedor
     * @param ruc Ruc del proveedor
     * @param name Nombre del proveedor
     * @param tel Telefono del proveedor
     * @param email Correo del proveedor
     * @return Codigo de respuesta
     */
    public int save(String ruc, String name, String tel, String email){
        /*List<Product> products = new ArrayList<>();
        loadData(products);
        int isExist = this.isProductExist(products, Product.builder().get.build());
        if(isExist != 0){
            return isExist;
        }
        int id = products.isEmpty() ? 0 : products.get(products.size() - 1).getId() + 1;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("products.json",StandardCharsets.UTF_8)) {
            Product product = Product.builder().id(id).ruc(ruc).name(name).tel(tel).email(email).build();
            products.add(product);
            gson.toJson(products, writer);
            this.products.add(0,product);
            return 0;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 1;
        }*/
        return 0;
    }
    /**
     * Edita los datos del proveedor
     * @param id Id del proveedor
     * @param localIndex Indice de la lista de proveedores ordenada por nombre
     * @param ruc Ruc del proveedor
     * @param name Nombre del proveedor
     * @param tel Telefono del proveedor
     * @param email Correo del proveedor
     * @return Codigo de respuesta
     */
    public int update(int id, int localIndex, String ruc, String name, String tel, String email) {
        /*List<Product> products = new ArrayList<>();
        loadData(products);
        int index = binarySearch(products, id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(index >= 0){
            if(products.get(index).getRuc().equalsIgnoreCase(ruc)
                    && products.get(index).getName().equalsIgnoreCase(name)
                    && products.get(index).getEmail().equalsIgnoreCase(email)
                    && products.get(index).getTel().equalsIgnoreCase(tel)){
                return 0;
            }
            int isExist = this.isProductExist(products, Product.builder().id(id).ruc(ruc).name(name).email(email).tel(tel).build());
            if(isExist != 0){
                return isExist;
            }
            try (FileWriter writer = new FileWriter("products.json",StandardCharsets.UTF_8)) {
                products.get(index).setRuc(ruc);
                products.get(index).setName(name);
                products.get(index).setTel(tel);
                products.get(index).setEmail(email);
                gson.toJson(products, writer);
                this.products.get(localIndex).setRuc(ruc);
                this.products.get(localIndex).setName(name);
                this.products.get(localIndex).setTel(tel);
                this.products.get(localIndex).setEmail(email);
                return 0;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return 1;
            }
        }else{
            return 3;
        }*/
        return 0;
    }
    
    public boolean delete(int id, int localIndex) {
        List<Product> products = new ArrayList<>();
        loadData(products);
        int index = binarySearch(products, id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(index >= 0){
            try (FileWriter writer = new FileWriter("products.json",StandardCharsets.UTF_8)) {
                products.remove(index);
                gson.toJson(products, writer);
                this.products.remove(localIndex);
                return true;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * Verifica que los datos del proveedor no existan
     * @param products
     * @param productToCheck
     * @return Regresa 0 si todo sale bien
     */
    public int isProductExist(List<Product> products, Product productToCheck) {
        /*for (Product product : products) {
            if(product.getId() != productToCheck.getId()) {
               if(product.getRuc().equalsIgnoreCase(productToCheck.getRuc())){
                    return 21;
                }
                if (product.getName().equalsIgnoreCase(productToCheck.getName())) {
                    return 22;
                }
                if(product.getTel().equalsIgnoreCase(productToCheck.getTel())) {
                    return 23;
                }
                if(product.getEmail().equalsIgnoreCase(productToCheck.getEmail())) {
                    return 24;
                } 
            }
        }*/
        return 0;
    }
    
    private void refreshData() {
        loadData(this.products);
        quicksort(this.products);
    }
    
    private void quicksort(List<Product> products){
        if(products == null || products.isEmpty()) {
            return;
        }
        sort(products,0,products.size() - 1);        
    }
    
    private void sort(List<Product> products, int left, int right){
        /*int i = left;
        int j = right;
        Product pivot = products.get(left + (right - left) / 2);
        while(i <= j) {
            while(products.get(i).compareTo(pivot) < 0){
                i++;
            }
            while(products.get(j).compareTo(pivot) > 0){
                j--;
            }
            if(i <= j){
                swap(products, i, j);
                i++;
                j--;
            }
        }*/
    }
    
    private void swap(List<Product> products, int i, int j){
        Product temp = products.get(i);
        products.set(i,products.get(j));
        products.set(j,temp);
    }
    
    private static int binarySearch(List<Product> products, int id) {
        int start = 0;
        int end = products.size() - 1;
        
        while(start <= end) {
            int middle = start + (end - start) / 2;
            Product currentProduct = products.get(middle);
            int currentId = currentProduct.getId();
            
            if(currentId == id) {
                return middle;
            }else if(currentId < id) {
                start = middle + 1;
            }else {
                end = middle - 1;
            }
        }
        return -1;
    }
}

package com.nanoka.almacenrepuestos.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nanoka.almacenrepuestos.dtos.ProductDto;
import com.nanoka.almacenrepuestos.models.Product;
import com.nanoka.almacenrepuestos.models.Category;
import com.nanoka.almacenrepuestos.models.Supplier;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private final List<ProductDto> productsDto = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();
    private final List<Supplier> suppliers = new ArrayList<>();
    
    public final CategoryService categoryService = new CategoryService();
    public final SupplierService supplierService = new SupplierService();
    
    public ProductService() {
        this.categoryService.loadData(this.categories);
        this.supplierService.loadData(this.suppliers);
        
        this.categoryService.sortData();
        this.supplierService.sortData();
    }

    private void loadData() {
        this.productsDto.clear();
        this.products.clear();
        try (FileReader reader = new FileReader("products.json",StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            this.productsDto.addAll(gson.fromJson(reader, new TypeToken<List<ProductDto>>(){}.getType()));
            for (ProductDto productDto : productsDto) {
                Product product = Product.builder().id(productDto.getId())
                        .name(productDto.getName())
                        .measurementUnit(productDto.getMeasurementUnit())
                        .category(categories.get(categoryService.binarySearch(categories, productDto.getCategoryId())))
                        .supplier(suppliers.get(supplierService.binarySearch(suppliers, productDto.getSupplierId())))
                        .stock(productDto.getStock())
                        .stockMin(productDto.getStockMin())
                        .price(productDto.getPrice())
                        .build();
                this.products.add(product);
            }
            quicksort();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public List<Product> getProducts() {
        this.loadData();
        return this.products;
    }
    
    public List<Category> getCategories() {
        return this.categories;
    }
    
    public List<Supplier> getSuppliers() {
        return this.suppliers;
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
    public int save(Category category, Supplier supplier, String name, String measurementUnit, int stock, int stockMin, BigDecimal price){
        int isExist = this.isProductExist(this.products, Product.builder().name(name).build());
        if(isExist != 0){
            return isExist;
        }
        int id = (productsDto.isEmpty() ? 0 : this.productsDto.get(this.productsDto.size() - 1).getId()) + 1;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("products.json",StandardCharsets.UTF_8)) {
            ProductDto productDto = ProductDto.builder()
                                    .id(id)
                                    .name(name)
                                    .measurementUnit(measurementUnit)
                                    .categoryId(category.getId())
                                    .supplierId(supplier.getId())
                                    .stock(stock)
                                    .stockMin(stockMin)
                                    .price(price)
                                    .build();
            this.productsDto.add(productDto);
            gson.toJson(this.productsDto, writer);
            this.quicksort();
            return 0;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 1;
        }
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
    public int update(int id, Category category, Supplier supplier, String name, String measurementUnit, int stock, int stockMin, BigDecimal price) {
        int index = binarySearch(id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(index >= 0){
            int isExist = this.isProductExist(products, Product.builder().id(id).name(name).build());
            if(isExist != 0){
                return isExist;
            }
            try (FileWriter writer = new FileWriter("products.json",StandardCharsets.UTF_8)) {
                this.productsDto.get(index).setName(name);
                this.productsDto.get(index).setMeasurementUnit(measurementUnit);
                this.productsDto.get(index).setCategoryId(category.getId());
                this.productsDto.get(index).setSupplierId(supplier.getId());
                this.productsDto.get(index).setStock(stock);
                this.productsDto.get(index).setStockMin(stockMin);
                this.productsDto.get(index).setPrice(price);
                gson.toJson(this.productsDto, writer);
                return 0;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return 1;
            }
        }else{
            return 3;
        }
    }
    
    public boolean delete(int id) {
        int index = binarySearch(id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(index >= 0){
            try (FileWriter writer = new FileWriter("products.json",StandardCharsets.UTF_8)) {
                this.productsDto.remove(index);
                gson.toJson(this.productsDto, writer);
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
        for (Product product : products) {
            if(product.getId() != productToCheck.getId() && product.getName().equalsIgnoreCase(productToCheck.getName())) {
                return 2;
            }
        }
        return 0;
    }
    
    public void quicksort(){
        if(this.products == null || this.products.isEmpty()) {
            return;
        }
        sort(this.products,0,this.products.size() - 1);        
    }
    
    private void sort(List<Product> products, int left, int right){
        int i = left;
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
        }
        
        if(left < j) {
            sort(products, left, j);
        }
        
        if(i < right){
            sort(products, i, right);
        }
    }
    
    private void swap(List<Product> products, int i, int j){
        Product temp = products.get(i);
        products.set(i,products.get(j));
        products.set(j,temp);
    }
    
    private int binarySearch(int id) {
        int start = 0;
        int end = this.productsDto.size() - 1;
        
        while(start <= end) {
            int middle = start + (end - start) / 2;
            ProductDto currentProductDto = this.productsDto.get(middle);
            int currentId = currentProductDto.getId();
            
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

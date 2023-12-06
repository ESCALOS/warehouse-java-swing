/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nanoka.almacenrepuestos.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.IOException;
import com.nanoka.almacenrepuestos.models.Category;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author carlo
 */
public class CategoryService {

    public List<Category> categories = new ArrayList<>();
    
    public void loadData(List<Category> categories) {
        categories.clear();
        try (FileReader reader = new FileReader("categories.json",StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            categories.addAll(gson.fromJson(reader, new TypeToken<List<Category>>(){}.getType()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public List<Category> getCategories() {
        this.sortData();
        return this.categories;
    }
    
    public List<Category> search(String search) {
        List<Category> matching = new ArrayList<>();
        for(Category category : this.categories){
            if(category.getName().toLowerCase().contains(search.toLowerCase())){
                matching.add(category);
            }
        }
        return matching;
    }
    
    public int save(String name){
        List<Category> categories = new ArrayList<>();
        loadData(categories);
        if(this.isCategoryExist(categories,Category.builder().name(name).build())){
            return 2;
        }
        int id = (categories.isEmpty() ? 0 : categories.get(categories.size() - 1).getId()) + 1;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("categories.json",StandardCharsets.UTF_8)) {
            Category category = Category.builder().id(id).name(name).build();
            categories.add(category);
            gson.toJson(categories, writer);
            return 0;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }
    
    public int update(int id, String name) {
        List<Category> categories = new ArrayList<>();
        loadData(categories);
        int index = binarySearch(categories, id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(index >= 0){
            if(categories.get(index).getName().equalsIgnoreCase(name)){
                return 0;
            }
            if(this.isCategoryExist(categories,Category.builder().id(id).name(name).build())){
                return 2;
            }
            try (FileWriter writer = new FileWriter("categories.json",StandardCharsets.UTF_8)) {
                categories.get(index).setName(name);
                gson.toJson(categories, writer);
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
        List<Category> categories = new ArrayList<>();
        loadData(categories);
        int index = binarySearch(categories, id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(index >= 0){
            try (FileWriter writer = new FileWriter("categories.json",StandardCharsets.UTF_8)) {
                categories.remove(index);
                gson.toJson(categories, writer);
                return true;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }else{
            return false;
        }
    }
    
    public boolean isCategoryExist(List<Category> categories, Category categoryToCheck) {
        for (Category category : categories) {
            if(category.getId() != categoryToCheck.getId() && category.getName().equalsIgnoreCase(categoryToCheck.getName())){
                return true;
            }
        }
        return false;
    }
    
    public void sortData() {
        loadData(this.categories);
        quicksort();
    }
    
    private void quicksort() {
        if(this.categories == null || this.categories.isEmpty()) {
            return;
        }
        sort(this.categories, 0, this.categories.size() - 1);
    }
    
    private static void sort(List<Category> categories, int left, int right) {
        int i = left;
        int j = right;
        Category pivot = categories.get(left + (right - left) / 2);
        while(i <= j) {
            while(categories.get(i).compareTo(pivot) < 0){
                i++;
            }
            while(categories.get(j).compareTo(pivot) > 0){
                j--;
            }
            if(i <= j){
                swap(categories, i, j);
                i++;
                j--;
            }
        }
        
        if(left < j) {
            sort(categories, left, j);
        }
        
        if(i < right){
            sort(categories, i, right);
        }
    }
    
    private static void swap(List<Category> categories, int i, int j){
        Category temp = categories.get(i);
        categories.set(i, categories.get(j));
        categories.set(j, temp);
    }
    
    public int binarySearch(List<Category> categories, int id) {
        int start = 0;
        int end = categories.size() - 1;
        
        while(start <= end) {
            int middle = start + (end - start) / 2;
            Category currentCategory = categories.get(middle);
            int currentId = currentCategory.getId();
            
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

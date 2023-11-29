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
    
    public CategoryService() {
        this.refreshData();
    }
    
    private void loadData(List<Category> categories) {
        categories.clear();
        try (FileReader reader = new FileReader("categories.json",StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            categories.addAll(gson.fromJson(reader, new TypeToken<List<Category>>(){}.getType()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public List<Category> getCategories() {
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
        int id = categories.isEmpty() ? 0 : categories.get(categories.size() - 1).getId() + 1;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(this.isCategoryNameExist(categories,name)){
            return 2;
        }
        try (FileWriter writer = new FileWriter("categories.json",StandardCharsets.UTF_8)) {
            Category category = new Category(id,name);
            categories.add(category);
            gson.toJson(categories, writer);
            this.categories.add(0,category);
            return 0;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }
    
    public int update(int id, int localIndex, String name) {
        List<Category> categories = new ArrayList<>();
        loadData(categories);
        int index = binarySearch(categories, id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(index >= 0){
            if(categories.get(index).getName().equalsIgnoreCase(name)){
                return 0;
            }
            if(this.isCategoryNameExist(categories,name)){
                return 2;
            }
            try (FileWriter writer = new FileWriter("categories.json",StandardCharsets.UTF_8)) {
                categories.get(index).setName(name);
                gson.toJson(categories, writer);
                this.categories.get(localIndex).setName(name);
                return 0;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return 1;
            }
        }else{
            return 3;
        }
    }
    
    public boolean delete(int id, int localIndex) {
        List<Category> categories = new ArrayList<>();
        loadData(categories);
        int index = binarySearch(categories, id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(index >= 0){
            try (FileWriter writer = new FileWriter("categories.json",StandardCharsets.UTF_8)) {
                categories.remove(index);
                gson.toJson(categories, writer);
                this.categories.remove(localIndex);
                return true;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }else{
            return false;
        }
    }
    
    public boolean isCategoryNameExist(List<Category> categories, String nameToCheck) {
        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase(nameToCheck)) {
                return true;
            }
        }
        return false;
    }
    
    private void refreshData() {
        loadData(this.categories);
        quicksort(this.categories);
    }
    
    private static void quicksort(List<Category> categories) {
        if(categories == null || categories.isEmpty()) {
            return;
        }
        sort(categories, 0, categories.size() - 1);
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
    
    private static int binarySearch(List<Category> categories, int id) {
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

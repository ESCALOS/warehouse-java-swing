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
import com.nanoka.almacenrepuestos.models.Supplier;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlo
 */
public class SupplierService {
    public List<Supplier> suppliers = new ArrayList<>();
    
    public SupplierService() {
        this.refreshData();
    }

    private void loadData(List<Supplier> suppliers) {
        suppliers.clear();
        try (FileReader reader = new FileReader("suppliers.json",StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            suppliers.addAll(gson.fromJson(reader, new TypeToken<List<Supplier>>(){}.getType()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public List<Supplier> getSuppliers() {
        return this.suppliers;
    }
    
    public List<Supplier> search(String search) {
        List<Supplier> matching = new ArrayList<>();
        for(Supplier supplier : this.suppliers){
            if(supplier.getName().toLowerCase().contains(search.toLowerCase())){
                matching.add(supplier);
            }
        }
        return matching;
    }
    
    public int save(String ruc, String name, String tel, String email){
        List<Supplier> suppliers = new ArrayList<>();
        loadData(suppliers);
        int id = suppliers.isEmpty() ? 0 : suppliers.get(suppliers.size() - 1).getId() + 1;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("suppliers.json",StandardCharsets.UTF_8)) {
            Supplier supplier = Supplier.builder().id(id).name(name).tel(tel).email(email).build();
            suppliers.add(supplier);
            gson.toJson(suppliers, writer);
            this.suppliers.add(0,supplier);
            return 0;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }
    
    public int update(int id, int localIndex, Supplier supplier) {
        List<Supplier> suppliers = new ArrayList<>();
        loadData(suppliers);
        int index = binarySearch(suppliers, id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(index >= 0){
            if(suppliers.get(index).getName().equalsIgnoreCase(supplier.getName())){
                return 0;
            }
            if(this.isSupplierNameExist(suppliers,supplier.getName())){
                return 2;
            }
            try (FileWriter writer = new FileWriter("suppliers.json",StandardCharsets.UTF_8)) {
                suppliers.get(index).setName(supplier.getName());
                gson.toJson(suppliers, writer);
                this.suppliers.get(localIndex).setName(supplier.getName());
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
        List<Supplier> suppliers = new ArrayList<>();
        loadData(suppliers);
        int index = binarySearch(suppliers, id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(index >= 0){
            try (FileWriter writer = new FileWriter("suppliers.json",StandardCharsets.UTF_8)) {
                suppliers.remove(index);
                gson.toJson(suppliers, writer);
                this.suppliers.remove(localIndex);
                return true;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }else{
            return false;
        }
    }
    
    public boolean isSupplierNameExist(List<Supplier> suppliers, String nameToCheck) {
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equalsIgnoreCase(nameToCheck)) {
                return true;
            }
        }
        return false;
    }
    
    private void refreshData() {
        loadData(this.suppliers);
        quicksort(this.suppliers);
    }
    
    private void quicksort(List<Supplier> suppliers){
        if(suppliers == null || suppliers.isEmpty()) {
            return;
        }
        sort(suppliers,0,suppliers.size() - 1);        
    }
    
    private void sort(List<Supplier> suppliers, int left, int right){
        int i = left;
        int j = right;
        Supplier pivot = suppliers.get(left + (right - left) / 2);
        while(i <= j) {
            while(suppliers.get(i).compareTo(pivot) < 0){
                i++;
            }
            while(suppliers.get(j).compareTo(pivot) > 0){
                j--;
            }
            if(i <= j){
                swap(suppliers, i, j);
                i++;
                j--;
            }
        }
    }
    
    private void swap(List<Supplier> suppliers, int i, int j){
        Supplier temp = suppliers.get(i);
        suppliers.set(i,suppliers.get(j));
        suppliers.set(j,temp);
    }
    
    private static int binarySearch(List<Supplier> suppliers, int id) {
        int start = 0;
        int end = suppliers.size() - 1;
        
        while(start <= end) {
            int middle = start + (end - start) / 2;
            Supplier currentSupplier = suppliers.get(middle);
            int currentId = currentSupplier.getId();
            
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

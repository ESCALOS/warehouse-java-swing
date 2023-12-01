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
        this.refreshData();
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
    /**
     * Guarda al proveedor
     * @param ruc Ruc del proveedor
     * @param name Nombre del proveedor
     * @param tel Telefono del proveedor
     * @param email Correo del proveedor
     * @return Codigo de respuesta
     */
    public int save(String ruc, String name, String tel, String email){
        List<Supplier> suppliers = new ArrayList<>();
        loadData(suppliers);
        int isExist = this.isSupplierExist(suppliers, Supplier.builder().ruc(ruc).name(name).email(email).tel(tel).build());
        if(isExist != 0){
            return isExist;
        }
        int id = suppliers.isEmpty() ? 0 : suppliers.get(suppliers.size() - 1).getId() + 1;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("suppliers.json",StandardCharsets.UTF_8)) {
            Supplier supplier = Supplier.builder().id(id).ruc(ruc).name(name).tel(tel).email(email).build();
            suppliers.add(supplier);
            gson.toJson(suppliers, writer);
            this.suppliers.add(0,supplier);
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
    public int update(int id, int localIndex, String ruc, String name, String tel, String email) {
        List<Supplier> suppliers = new ArrayList<>();
        loadData(suppliers);
        int index = binarySearch(suppliers, id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(index >= 0){
            if(suppliers.get(index).getRuc().equalsIgnoreCase(ruc)
                    && suppliers.get(index).getName().equalsIgnoreCase(name)
                    && suppliers.get(index).getEmail().equalsIgnoreCase(email)
                    && suppliers.get(index).getTel().equalsIgnoreCase(tel)){
                return 0;
            }
            int isExist = this.isSupplierExist(suppliers, Supplier.builder().id(id).ruc(ruc).name(name).email(email).tel(tel).build());
            if(isExist != 0){
                return isExist;
            }
            try (FileWriter writer = new FileWriter("suppliers.json",StandardCharsets.UTF_8)) {
                suppliers.get(index).setRuc(ruc);
                suppliers.get(index).setName(name);
                suppliers.get(index).setTel(tel);
                suppliers.get(index).setEmail(email);
                gson.toJson(suppliers, writer);
                this.suppliers.get(localIndex).setRuc(ruc);
                this.suppliers.get(localIndex).setName(name);
                this.suppliers.get(localIndex).setTel(tel);
                this.suppliers.get(localIndex).setEmail(email);
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
    /**
     * Verifica que los datos del proveedor no existan
     * @param suppliers
     * @param supplierToCheck
     * @return Regresa 0 si todo sale bien
     */
    public int isSupplierExist(List<Supplier> suppliers, Supplier supplierToCheck) {
        for (Supplier supplier : suppliers) {
            if(supplier.getId() != supplierToCheck.getId()) {
               if(supplier.getRuc().equalsIgnoreCase(supplierToCheck.getRuc())){
                    return 21;
                }
                if (supplier.getName().equalsIgnoreCase(supplierToCheck.getName())) {
                    return 22;
                }
                if(supplier.getTel().equalsIgnoreCase(supplierToCheck.getTel())) {
                    return 23;
                }
                if(supplier.getEmail().equalsIgnoreCase(supplierToCheck.getEmail())) {
                    return 24;
                } 
            }
        }
        return 0;
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

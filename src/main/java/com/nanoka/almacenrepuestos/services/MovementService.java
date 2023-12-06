package com.nanoka.almacenrepuestos.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nanoka.almacenrepuestos.dtos.MovementDto;
import com.nanoka.almacenrepuestos.dtos.ProductDto;
import com.nanoka.almacenrepuestos.models.Product;
import com.nanoka.almacenrepuestos.models.Movement;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MovementService {
    private final List<Product> products = new ArrayList<>();
    private final List<Movement> movements = new ArrayList<>();
    private final Stack<MovementDto> movementsDto = new Stack<>();
    
    private final ProductService productService = new ProductService();
    
    public MovementService() {
        this.products.addAll(this.productService.getProducts());
        
    }
}

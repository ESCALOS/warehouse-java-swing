package com.nanoka.almacenrepuestos.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nanoka.almacenrepuestos.adapters.LocalDateTimeAdapter;
import com.nanoka.almacenrepuestos.adapters.MovementTypeAdapter;
import com.nanoka.almacenrepuestos.dtos.MovementDto;
import com.nanoka.almacenrepuestos.dtos.ProductDto;
import java.math.RoundingMode;
import com.nanoka.almacenrepuestos.models.Movement;
import com.nanoka.almacenrepuestos.models.MovementType;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MovementService {
    private final List<Movement> movements = new ArrayList<>();
    private final Stack<MovementDto> movementsDto = new Stack<>();
    
    public final ProductService productService = new ProductService();
    
    public MovementService() {
        this.productService.loadData();
        loadData();
    }
    
    private void loadData() {
        try (FileReader reader = new FileReader("movements.json",StandardCharsets.UTF_8)) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
            gsonBuilder.registerTypeAdapter(MovementType.class, new MovementTypeAdapter());
            Gson gson = gsonBuilder.create();
            List<MovementDto> movementsDtoList = new ArrayList<>();
            movementsDtoList.addAll(gson.fromJson(reader, new TypeToken<List<MovementDto>>(){}.getType()));
            for(MovementDto movementDto : movementsDtoList){
                this.movements.add(0,this.dtoToMovement(movementDto));
                this.movementsDto.push(movementDto);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public Movement dtoToMovement(MovementDto movementDto) {
        return Movement.builder()
                .id(movementDto.getId())
                .movementType(movementDto.getMovementType())
                .product(this.productService.dtoToProduct( this.productService.getProductDto(movementDto.getProductId())))
                .quantity(movementDto.getQuantity())
                .price(movementDto.getPrice())
                .datetime(movementDto.getDatetime())
                .build();
    }
    
    public List<Movement> getMovements() {
        return this.movements;
    }
    
    private int save() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(MovementType.class, new MovementTypeAdapter())
            .setPrettyPrinting()
            .create();
        try (FileWriter writer = new FileWriter("movements.json",StandardCharsets.UTF_8)) {
            gson.toJson(this.movementsDto, writer);
            return 0;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }
    
    public int input(int productId, int quantity, BigDecimal price){
        int id = (movementsDto.isEmpty() ? 0 : movementsDto.peek().getId()) + 1;
        MovementDto movementDto = MovementDto.builder()
                                    .id(id)
                                    .movementType(MovementType.Ingreso)
                                    .productId(productId)
                                    .quantity(quantity)
                                    .price(price)
                                    .datetime(LocalDateTime.now())
                                    .build();
        int state = this.productService.updateStock(MovementType.Ingreso, productId, quantity, price);
        if(state == 0) {
            this.movementsDto.push(movementDto);
            int code = this.save();
            this.movements.add(0,this.dtoToMovement(movementDto));
            return code;
        }else{
            return state;
        }
    }
    
    public int output(int productId, int quantity) {
        int id = (movementsDto.isEmpty() ? 0 : movementsDto.peek().getId()) + 1;
        ProductDto productDto = this.productService.getProductDto(productId);
        if(quantity > productDto.getStock()) {
            return 2;
        }
        BigDecimal price = BigDecimal.valueOf(quantity).divide(BigDecimal.valueOf(productDto.getStock())).multiply(productDto.getPrice());
        price = price.setScale(2, RoundingMode.HALF_UP);
        MovementDto movementDto = MovementDto.builder()
                                    .id(id)
                                    .movementType(MovementType.Salida)
                                    .productId(productId)
                                    .quantity(quantity)
                                    .price(price)
                                    .datetime(LocalDateTime.now())
                                    .build();
        int state = this.productService.updateStock(MovementType.Salida, productId, quantity, price);
        if(state == 0) {
            this.movementsDto.push(movementDto);
            int code = this.save();
            this.movements.add(0,this.dtoToMovement(movementDto));
            return code;
        }else{
            return state;
        }
    }
    
    public int cancel() {
        MovementDto movementDto = this.movementsDto.peek();
        MovementType movementType = movementDto.getMovementType().equals(MovementType.Ingreso) ? MovementType.Salida : MovementType.Ingreso;
        int state = this.productService.updateStock(movementType, movementDto.getProductId(), movementDto.getQuantity(), movementDto.getPrice());
        if(state == 0) {
            this.movementsDto.pop();
            int code = this.save();
            this.movements.remove(0);
            return code;
        }else{
            return state;
        }
        
    }
}

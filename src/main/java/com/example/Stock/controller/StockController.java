package com.example.Stock.controller;

import com.example.Stock.dto.StockRequest;
import com.example.Stock.dto.StockResponse;
import com.example.Stock.model.Stock;
import com.example.Stock.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@AllArgsConstructor
@RestController
@RequestMapping("/stocks")
public class StockController {

    private StockService stockService;

    //second part after using //3rd part using DTo also

    @GetMapping("/id")
    public Mono<StockResponse> getOneStock(@PathVariable String id){

        return stockService.getOneStock(id);
    }

    @GetMapping
    public Flux<StockResponse> getAllStock(@RequestParam (required = false, defaultValue = "0") BigDecimal priceGreaterThan){

        return stockService.getAllStock(priceGreaterThan);
    }

    @PostMapping
    public Mono<StockResponse> createStock(@RequestBody StockRequest stock){

        return stockService.createStock(stock);
    }


}

/*
    //first part
    @GetMapping("/id")
    public Optional<Stock> getOneStock(@PathVariable String id){
        return stockService.getOneStock(id);
    }

    @GetMapping
    public Iterable<Stock> getAllStock(){
        return stockService.getAllStock();

    }

    @PostMapping
    public Stock createStock(@RequestBody Stock stock){
    return stockService.createStock(stock);
    }
*/
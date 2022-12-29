package com.example.Stock.service;

import com.example.Stock.dto.StockRequest;
import com.example.Stock.dto.StockResponse;
import com.example.Stock.exception.StockCreationException;
import com.example.Stock.exception.StockNotFoundException;
import com.example.Stock.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

//End with peek operation
@Slf4j
@AllArgsConstructor
public class StockServiceWithPeekOperations {

    private StockRepository stockRepository;


    public Mono<StockResponse> getOneStock(String id){
        return stockRepository.findById(id)
                .map(StockResponse::fromModel)
                .switchIfEmpty(Mono.error(
                        new StockNotFoundException(
                                "Stock not found with id: "+ id)))
                .doFirst(()->log.info("retrieving stock with id{}",id))
                .doOnNext(stock->log.info("stock found {}:",stock))
                .doOnError(ex->log.error("something went wrong while retrieving the stock with id:{} ",id,ex))
                .doOnTerminate(()->log.info("Finalized retrieving stock"))
                .doFinally(singleType-> log.info("Finalized retrieving stock with single type: {}", singleType));
    }

    public Flux<StockResponse> getAllStock(BigDecimal priceGreaterThan){// add filter
        return stockRepository.findAll()
                .filter(stock ->
                        stock.getPrice().compareTo(priceGreaterThan)>0)
                .map(StockResponse::fromModel);
    }

    public Mono<StockResponse> createStock(StockRequest stock){
        //will not run
//        return stockRepository.save(stock.toModel())
//                .map(StockResponse::fromModel)
//                .onErrorReturn(StockResponse.builder().build());

        //should like this: encapsulate it
        /*onErrorReturn */

//        return Mono.just(stock)
//                .map(StockRequest::toModel)
//                .flatMap(st-> stockRepository.save(st))
//                .map(StockResponse::fromModel)
//                .onErrorReturn(StockResponse.builder().build());
//
        /*onErrorResume */

        return Mono.just(stock)
                .map(StockRequest::toModel)
                .flatMap(st-> stockRepository.save(st))
                .map(StockResponse::fromModel)
                .onErrorResume(ex ->{
                    log.warn("Exception thrown while creating a new stock: ", ex );
                    return Mono.just(StockResponse.builder().build());
                });

        /*onErrorMap */

//        return Mono.just(stock)
//                .map(StockRequest::toModel)
//                .flatMap(st-> stockRepository.save(st))
//                .map(StockResponse::fromModel)
//                .onErrorMap(ex-> new StockCreationException(ex.getMessage()));

    }
}


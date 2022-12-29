package com.example.Stock.service;


import com.example.Stock.dto.StockRequest;
import com.example.Stock.dto.StockResponse;
import com.example.Stock.exception.StockCreationException;
import com.example.Stock.exception.StockNotFoundException;
import com.example.Stock.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class StockService {
    //add Excep
    private StockRepository stockRepository;


    //3rd part using DTo also
    public Mono<StockResponse> getOneStock(String id){
        return stockRepository.findById(id)
                .map(StockResponse::fromModel)
                .switchIfEmpty(Mono.error(
                        new StockNotFoundException(
                                "Stock not found with id: "+ id)));
                /*Mono.error */
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



    /*
    //second part after using Reactive Repo
    public Mono<Stock> getOneStock(String id){
        return stockRepository.findById(id);
    }

    public Flux<Stock> getAllStock(){
        return stockRepository.findAll();
    }

    public Mono<Stock> createStock(Stock stock){
        return stockRepository.save(stock);
    }

*/

/*
* Object - > Mono<Object>
Optional <Object> -> Mono<Object>
* Collection <Object> -> Flux<Object>

Note: for h2( relation DB) -> R2DBC
* */




/* first part:
    // won't able working with Optional once using Mono/Flux and using Reactive Repo

    public Optional<Stock> getOneStock(String id){
        return stockRepository.findById(id);
    }

    public Iterable<Stock> getAllStock(){
        return stockRepository.findAll();
    }

    public Stock createStock(Stock stock){
        return stockRepository.save(stock);
    }
*/


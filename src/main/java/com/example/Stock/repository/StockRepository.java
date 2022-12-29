package com.example.Stock.repository;

import com.example.Stock.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock,String> {// switch the repo to Reac...
}

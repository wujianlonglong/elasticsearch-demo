package com.wjl.repository;

import com.wjl.domain.OrderProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderProductRepository extends ElasticsearchRepository<OrderProduct,Integer> {
}

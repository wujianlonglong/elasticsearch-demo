package com.wjl.repository;

import com.wjl.domain.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderRepository extends ElasticsearchRepository<Order,Long> {
}

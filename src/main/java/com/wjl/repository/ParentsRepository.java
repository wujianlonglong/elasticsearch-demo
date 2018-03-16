package com.wjl.repository;

import com.wjl.domain.Parents;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ParentsRepository extends ElasticsearchRepository<Parents,Integer> {
}

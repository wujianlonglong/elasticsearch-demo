package com.wjl.repository;

import com.wjl.domain.NestTest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NestTestRepository extends ElasticsearchRepository<NestTest,Long>{
}

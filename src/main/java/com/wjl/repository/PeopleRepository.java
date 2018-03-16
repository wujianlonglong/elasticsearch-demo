package com.wjl.repository;

import com.wjl.domain.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by wujianlong on 2017/7/11.
 */
public interface PeopleRepository extends ElasticsearchRepository<People,String>{
    List<People> findByName(String name);
    List<People> findByNameLike(String name);


    @Query("{\"bool\" : {\"must\" : {\"prefix\" : {\"name\" : \"?0\"}}}}")
    AggregatedPage<People> findByName(String name, Pageable pageable);
}

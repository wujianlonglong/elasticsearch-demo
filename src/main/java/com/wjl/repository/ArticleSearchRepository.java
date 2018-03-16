package com.wjl.repository;

import com.wjl.domain.Article;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


/**
 * Created by wujianlong on 2017/6/28.
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, String> {
    List<Article>  findByTitleLike(String title);
}

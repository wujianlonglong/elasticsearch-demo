package com.wjl.repository;


import com.wjl.domain.EsPlatformGoods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by wujianlong on 2017/7/19.
 */
public interface EsPlatformGoodsRepository extends ElasticsearchRepository<EsPlatformGoods,Integer> {

  //  Optional<EsPlatformGoods> findById(Integer id);

    //用于数据更新
    EsPlatformGoods findByPlatformIdAndSjGoodsCodeAndPlatformGoodsCode(String platId, String goodsCode, String pltCode);

    //用于判断平台号+三江商品编码对应的商品是否准确
    List<EsPlatformGoods> findByPlatformIdAndSjGoodsCode(String platId, String goodsCode);

    //用于判断平台号+三江商品编码对应的商品是否准确(非删除状态的)
    EsPlatformGoods findByPlatformIdAndSjGoodsCodeAndStatusNot(String platId, String goodsCode, Integer status);

    //用于判断平台号+外部商品编码对应的商品是否准确(非删除状态的)
    EsPlatformGoods findByPlatformIdAndPlatformGoodsCodeAndStatusNot(String platId, String goodsCode, Integer status);

    //根据商品编码查找商品
    List<EsPlatformGoods> findBySjGoodsCode(String goodsCode);

   // Page<EsPlatformGoods> findAll(Specification<EsPlatformGoods> platformGoodsSpecification, Pageable pageParam);

    //查询门店总数
//    @Modifying
//    @Transactional
//    @Query("update PLATFORM_PRODUCT set status=?1 where id=?2")
//    int updateStatus(Integer status, Integer id);


    //test
    //根据平台查询数量
    List<EsPlatformGoods> findByPlatformId(String platformId);

    List<EsPlatformGoods> findByPlatformIdAndStatusNot(String platformId, Integer status);

//    @Query(value = "select distinct(sj_goods_code) from platform_product", nativeQuery = true)
//    List<String> findAllGoodsCode();

    List<EsPlatformGoods> findByPlatformIdAndStatus(String platformId, Integer status);


    List<EsPlatformGoods>    findByIdIn(Integer[] ids);


}

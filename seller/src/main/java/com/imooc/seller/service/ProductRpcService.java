package com.imooc.seller.service;

import com.imooc.api.events.ProductStatusEvent;
import com.imooc.entity.Product;
import com.imooc.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * rpc相关服务
 */
@Service
public class ProductRpcService implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger LOG = LoggerFactory.getLogger(ProductRpcService.class);

    static final String MQ_DESTINATION = "Consumer.cache.VirtualTopic.PRODUCT_STATUS";

    @Autowired
    private ProductCache productCache;
    /**
     * 查询多个产品
     * @return
     */
    public List<Product> findAll(){
        return productCache.readAllCache();
    }

    /**
     * 查询单个产品
     * @param id
     * @return
     */
    public Product findOne(String id){
        Product product = productCache.readCache(id);
        if (product==null){
            productCache.removeCache(id);
        }
        return product;

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Product> products = findAll();
        products.forEach(product -> {
            productCache.putCache(product);
        });
    }

    @JmsListener(destination = MQ_DESTINATION)
    void updateCache(ProductStatusEvent productStatusEvent){
        LOG.info("receive event:{}",productStatusEvent);
        productCache.removeCache(productStatusEvent.getId());
        if (ProductStatus.IN_SELL.equals(productStatusEvent.getProductStatus())){
            productCache.readCache(productStatusEvent.getId());
        }
    }
}

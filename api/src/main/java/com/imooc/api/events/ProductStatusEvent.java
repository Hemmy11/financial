package com.imooc.api.events;

import com.imooc.entity.enums.ProductStatus;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class ProductStatusEvent implements Serializable {
    private String id;
    private ProductStatus productStatus;

    public ProductStatusEvent(String id, ProductStatus productStatus) {
        this.id = id;
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }
}

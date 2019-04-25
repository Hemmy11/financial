package com.imooc.api.domian;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.util.List;
@JsonDeserialize(as = ProductRpcReq.class)
public interface ParamInf {
    public List<String> getIdList();
    public BigDecimal getMinRewardRate();
    public BigDecimal getMaxRewardRate();
    public List<String> getStatusList();
}

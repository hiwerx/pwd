package com.lq.pwd.mapper;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HzMapper {

    int insertHz(Map<String,String> data);
    List<JSONObject> selectHz(Map<String,Object> data);
    int updateHz(Map<String,Object> data);

    List<Map<String, Object>> selectHzOrderByTime(Map<String, Object> reqParam);

}

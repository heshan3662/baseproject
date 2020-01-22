package com.example.login.Utils;

import com.alibaba.fastjson.JSON;
import com.example.login.entity.responsetype.ApiRes;


import java.io.IOException;

public class  ResponseUtils {

    public static ApiRes transResponseForObject(ApiRes ApiRes, String res, Class t){
        ApiRes.set_result_code(-1);
        if("".equals(res)|| res == null ){
            ApiRes.set_result_message("未获取到数据");
            return ApiRes;
        }
        try   {
            ApiRes.set_data( JSON.parseObject(res,t));
            ApiRes.set_result_code(0);
        } catch ( Exception e) {
            ApiRes.set_result_code(-1);
            ApiRes.set_result_message(e.toString());
        }
        return ApiRes;
    }
}

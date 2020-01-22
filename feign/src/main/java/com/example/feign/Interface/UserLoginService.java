package com.example.feign.Interface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-login")
public interface UserLoginService {
    @RequestMapping(value = "/auth/user_authority", method = RequestMethod.POST)
    boolean user_authority(@RequestParam(value = "token") String token ,
                           @RequestParam(value = "action_id") String action_id);
}

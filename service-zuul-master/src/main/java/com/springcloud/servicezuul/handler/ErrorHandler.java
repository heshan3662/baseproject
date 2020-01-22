package com.springcloud.servicezuul.handler;

import com.springcloud.servicezuul.responsetype.DataRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 接口请求异常错误捕获
 * @author heshan
 * @date 2020/1/13
 */

@RestController
public class ErrorHandler implements ErrorController {
    private final ErrorAttributes errorAttributes;

    @Autowired
    public ErrorHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String error(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, true);
        String msg = errorAttributes.getOrDefault("error", "not found").toString();
        String code = errorAttributes.getOrDefault("status", 404).toString();
        String path =     errorAttributes.getOrDefault("path", "./").toString();
        DataRes dataRes = new DataRes(path, new Integer(code), msg);
        return dataRes.toString() ;
    }
}

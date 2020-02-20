package com.example.demo.entity.responsetype;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ApiError" ,description = "错误返回")
public class ApiError {

    @ApiModelProperty(value = "编码")
    private String code;

    public String getDevelopermessage() {
        return developermessage;
    }

    @ApiModelProperty(value = "说明")
    private String message;

    @ApiModelProperty(value = "开发人员说明")
    private String developermessage;

    @ApiModelProperty(value = "状态")
    private String status;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setDevelopermessage(String developermessage) {
        this.developermessage = developermessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
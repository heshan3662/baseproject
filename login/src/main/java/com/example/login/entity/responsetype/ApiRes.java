package com.example.login.entity.responsetype;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ApiRes" ,description = "接口返回")
public class ApiRes<T> {

//    @ApiModelProperty(value = "接口名")
//    private String _action = "";
    @ApiModelProperty(value = "返回值，-1 ：错误，0 ：正常")
    private int _result_code   ;
    @ApiModelProperty(value = "结果说明")
    private String _result_message = "";
    @ApiModelProperty(value = "返回数据")
    private T _data = null;
    @ApiModelProperty(value = "总条数")
    private Long  _total ;
    public T get_data() {
        return _data;
    }

    public Long get_total() {
        return _total;
    }

    public void set_total(Long _total) {
        this._total = _total;
    }
//    public String get_action() {
//        return _action;
//    }

    public int get_result_code() {
        return _result_code;
    }

    public String get_result_message() {
        return _result_message;
    }

//    public void set_action(String _action) {
//        this._action = _action;
//    }

    public void set_result_code(int _result_code) {
        this._result_code = _result_code;
    }

    public void set_result_message(String _result_message) {
        this._result_message = _result_message;
    }
    public void set_data(T _data) {
        this._data = _data;
    }
}
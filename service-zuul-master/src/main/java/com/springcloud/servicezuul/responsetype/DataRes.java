package com.springcloud.servicezuul.responsetype;



import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataRes {

    private String _path = "";
    private int _result_code =  ResultCode.RESULT_SUCCESS;
    private String _result_message = "";
    private Integer _total = null;
    //private int _count = 0;
    private List<Map<String, Object>> _rows = null;
    private Map<String, Object> _data = null;

    public String get_path() {
        return _path;
    }

    public int get_result_code() {
        return _result_code;
    }

    public String get_result_message() {
        return _result_message;
    }

    public Integer get_total() {
        return _total;
    }

    public List<Map<String, Object>> get_rows() {
        return _rows;
    }

    public Map<String, Object> get_data() {
        return _data;
    }

    public Map<String, Object> toJSON() {
        Map<String, Object> _obj = new LinkedHashMap<String, Object>();
        _obj.put("action", this._path);
        _obj.put("result_code", this._result_code );
        _obj.put("result_message", this._result_message);
        if( this._total != null ){
            _obj.put("total", this._total);
        }
        if( this._rows != null ) {
            _obj.put("count",  this._rows.size());
            _obj.put("rows", this._rows);
        }
        if( this._data != null ) {
            _obj.put("data", this._data);
        }
        return _obj;
    }

    public String toString() {
        return toJSON().toString();
    }

    public DataRes(String path, int resultCode,String resultMessage ) {
        this._path = path;
        this._result_code = resultCode;
        this._result_message = resultMessage;
    }

    public void setPath(String path) {
        this._path = path;
    }

    public void setResultCode(int resultCode) {
        this._result_code = resultCode;
    }

    public void setResultMessage(String resultMessage) {
        this._result_message = resultMessage;
    }

    public void setTotal(Integer total) {
        this._total = total;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this._rows = rows;
    }

    public void setData(Map<String, Object> data) {
        this._data = data;
    }

}

package ua.dborisenko.astergazer.domain;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "status", "code", "data" })
public class RestResult {

    private String status = "OK";
    
    private int code = 200;
    
    private Map<String, Object> data = new HashMap<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void addToData(String key, Object value) {
        this.data.put(key, value);
    }
}

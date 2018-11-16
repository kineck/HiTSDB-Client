package com.aliyun.hitsdb.client.value.response;

import com.alibaba.fastjson.annotation.JSONType;
import com.aliyun.hitsdb.client.value.JSONValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JSONType(ignores = {"dps"})
public class MultiValuedQueryLastResult extends JSONValue {
    private String name;
    private List<String> columns = new ArrayList<String>();
    private List<List<Object>> values = new ArrayList<List<Object>>();
    private List<Map<String, Object>> dps = new ArrayList<Map<String, Object>>();

    public String getName() {
        return name;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<List<Object>> getValues() {
        return values;
    }

    public List<Map<String, Object>> getDps() {
        return dps;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public void setValues(List<List<Object>> values) {
        this.values = values;
    }

    public void setDps(List<Map<String, Object>> dps) {
        this.dps = dps;
    }
}

package com.example.kvaru.mjalbum.datamodels;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainDataModelClass {

    private Integer resultCount;
    private List<Result> results = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
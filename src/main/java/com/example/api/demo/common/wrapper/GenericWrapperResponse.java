package com.example.api.demo.common.wrapper;

import java.util.List;

public class GenericWrapperResponse<T> {
    private List<T> data;
    private String version;

    public GenericWrapperResponse(List<T> data, String version) {
        this.data = data;
        this.version = version;
    }

    // Getters and setters
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

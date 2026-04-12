package com.example.api.demo.common.interfaces;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface EntityInterface {

    Long getId();

    void setId(Long id);

}

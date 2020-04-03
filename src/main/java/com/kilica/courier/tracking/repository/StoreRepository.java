package com.kilica.courier.tracking.repository;

import com.kilica.courier.tracking.model.entity.Store;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoreRepository extends CrudRepository<Store, Long> {

    List<Store> findAll();
}
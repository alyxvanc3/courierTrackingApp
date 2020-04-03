package com.kilica.courier.tracking.repository;

import com.kilica.courier.tracking.model.entity.Courier;
import org.springframework.data.repository.CrudRepository;

public interface CourierRepository extends CrudRepository<Courier, Long> {

    Courier findById(long id);
}

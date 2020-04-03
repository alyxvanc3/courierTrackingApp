package com.kilica.courier.tracking.service;

import com.kilica.courier.tracking.model.entity.Courier;
import com.kilica.courier.tracking.model.entity.Entrance;
import com.kilica.courier.tracking.model.entity.Store;
import com.kilica.courier.tracking.repository.CourierRepository;
import com.kilica.courier.tracking.repository.EntranceRepository;
import com.kilica.courier.tracking.repository.StoreRepository;
import com.kilica.courier.tracking.util.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CourierServiceImpl implements CourierService {

    public static final Double MAX_DISTANCE = 100D;
    public static final Long ONE_MINUTE = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private EntranceRepository entranceRepository;

    @Override
    @Transactional
    public Void logIfCourierinZone(final LocalDateTime time,
                                   final Long id,
                                   final Double latitude,
                                   final Double longitude) {

        boolean countAsEntrance = false;

        Courier courier;

        final Optional<Courier> optionalCourier = courierRepository.findById(id);

        if (optionalCourier.isPresent()){
            courier = optionalCourier.get();

            courier.setTotalDistance(courier.getTotalDistance() +
                    calculateDistanceBetweenTwoPoints(courier.getLatitude(), courier.getLongitude(),
                            latitude, longitude));
        }
        else {
            courier = new Courier();
            courier.setId(id);
            courier.setTotalDistance(new Double(0));
        }
        courier.setLatitude(latitude);
        courier.setLongitude(longitude);
        courier.setTime(time);
        courierRepository.save(courier);

        final List<Store> stores = storeRepository.findAll();

        for(Store store : stores) {
            final Double distance = MathUtil.calculateDistanceByHaversineFormula(
                    store.getLatitude(), store.getLongitude(), latitude, longitude);

            if(distance < MAX_DISTANCE) {

                Set<Entrance> entrances = courier.getEntrances();

                Optional<Entrance> optionalEntrance;

                if (entrances.size() > 0) {
                    optionalEntrance = entrances.stream()
                            .filter(entry -> entry.getStore().getId() == store.getId() )
                            .findFirst();

                    if(optionalEntrance.isPresent()) {
                        Entrance entrance = optionalEntrance.get();

                        if(isCountableAsEntrance(entrance.getEnteredAt(), time)) {
                            countAsEntrance = true;
                        }
                        entrance.setEnteredAt(time);

                        entranceRepository.save(entrance);
                    }
                    else {
                        saveAsNewEntrance(courier, store, time);

                        countAsEntrance = true;
                    }
                }
                else{
                    saveAsNewEntrance(courier, store, time);

                    countAsEntrance = true;
                }

                if(countAsEntrance) {
                    log.info("Courier " +id.toString() + " is entered the "
                            + store.getName().toString() +" store's circumference.");
                }
                courierRepository.save(courier);

                break;
            }
        }

        return null;
    }

    @Override
    public Double getTotalTravelDistance(final Long id) {
        final Optional<Courier> courier = courierRepository.findById(id);

        if (!courier.isPresent()) {
            return new Double(0);
        }

        return courier.get().getTotalDistance();
    }


    private void saveAsNewEntrance(final Courier courier, final Store store, final LocalDateTime time) {
        final Entrance entranceToSave = new Entrance();
        entranceToSave.setCourier(courier);
        entranceToSave.setStore(store);
        entranceToSave.setEnteredAt(time);

        Set<Entrance> savedEntrances = courier.getEntrances();

        savedEntrances.add(entranceToSave);

        courier.setEntrances(savedEntrances);
    }


    public boolean isCountableAsEntrance(final LocalDateTime savedTime, final LocalDateTime currentTime) {

        final LocalDateTime oneMinuteAfterSavedTime =savedTime.plusMinutes(ONE_MINUTE);

        return oneMinuteAfterSavedTime.isBefore(currentTime);
    }

    private Double calculateDistanceBetweenTwoPoints(final Double savedLatitude,
                                     final Double savedLongitude,
                                     final Double enteredLatitude,
                                     final Double enteredLongitude) {

        return Math.sqrt((enteredLongitude - savedLongitude) * (enteredLongitude - savedLongitude)
                + (enteredLatitude - savedLatitude) * (enteredLatitude - savedLatitude));
    }

}

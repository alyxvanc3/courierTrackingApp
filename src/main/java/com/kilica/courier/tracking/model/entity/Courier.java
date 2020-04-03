package com.kilica.courier.tracking.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "courier")
public class Courier {

    @Id
    private Long id;

    private Double latitude;

    private Double longitude;

    private LocalDateTime time;

    private Double totalDistance;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "courier")
    private Set<Entrance> entrances = new HashSet<Entrance>();

}

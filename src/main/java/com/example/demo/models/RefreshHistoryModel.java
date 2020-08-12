package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.LocalDate;

import javax.persistence.*;

public class RefreshHistoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @JsonIgnore
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column(name = "last_refreshed_time")
    private LocalDate lastRefreshedTime;
}

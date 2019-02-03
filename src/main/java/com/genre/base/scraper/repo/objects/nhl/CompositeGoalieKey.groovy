package com.genre.base.scraper.repo.objects.nhl

import com.fasterxml.jackson.annotation.JsonProperty

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Temporal
import javax.persistence.TemporalType

class CompositeGoalieKey implements Serializable {

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date createTimeStamp

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    public Integer id

    @Id
    @JsonProperty
    public String name


}

package com.genre.base.scraper.repo.objects.nhl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType


@JsonIgnoreProperties
@Entity
@Table(name="goalie_table")
@IdClass(CompositeGoalieKey.class)
class GoalieVO implements Serializable {

    // goalie name, teamName, isConfirmed, dateTimeOfGame

    @Id
    @JsonProperty
    public int id

    @Id
    @JsonProperty
    public String name

    @JsonProperty
    public String teamName

    @JsonProperty
    public int isConfirmed

    @JsonProperty
    public int wasSentToAllEmails // toggle this after success sending to all people subscribed

    @JsonProperty
    public String dateTimeOfGame

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updateTimeStamp

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date createTimeStamp

}

package com.genre.base.scraper.repo.objects.nhl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.persistence.Transient


@JsonIgnoreProperties
@Entity
@Table(name="goalie_table")
class GoalieVO implements Serializable {

    // goalie name, teamName, isConfirmed, dateTimeOfGame

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="nhl_game_id")
    NhlGameVO nhlGameVO

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    Long goalie_id

    @JsonProperty
    String name

    @JsonProperty
    String source

    @JsonProperty
    String goalieDesc

    @JsonProperty
    String teamName

    @JsonProperty
    int isConfirmed

    @JsonProperty
    int wasSentToAllEmails // toggle this after success sending to all people subscribed

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updateTimeStamp

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date createTimeStamp

    @Transient
    boolean isHydrated

}

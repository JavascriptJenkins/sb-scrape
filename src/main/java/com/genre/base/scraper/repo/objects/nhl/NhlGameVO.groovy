package com.genre.base.scraper.repo.objects.nhl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.persistence.*

@JsonIgnoreProperties
@Entity
@Table(name="nhl_game")
class NhlGameVO implements Serializable {

    @OneToMany(mappedBy="nhlGameVO",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    List<GoalieVO> goalieVOList;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    Long nhl_game_id

    @JsonProperty
    String gameDesc

    @JsonProperty
    String dateTimeOfGame

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updateTimeStamp

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date createTimeStamp

}

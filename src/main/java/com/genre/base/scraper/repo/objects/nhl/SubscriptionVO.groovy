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
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@JsonIgnoreProperties
@Entity
@Table(name="subscription")
class SubscriptionVO implements Serializable {

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    UserVO userVO

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="subscription_type_id")
    SubscriptionTypeVO subscriptionTypeVO

    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long subscription_id

    @JsonProperty
    Integer active // can be used by administrators to de-activate a subscription if need be (off season, etc.)

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    java.util.Date subscriptionStartDate // ie. start of hockey season

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    java.util.Date subscriptionEndDate // ie. end of hockey season

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updateTimeStamp

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date createTimeStamp

}

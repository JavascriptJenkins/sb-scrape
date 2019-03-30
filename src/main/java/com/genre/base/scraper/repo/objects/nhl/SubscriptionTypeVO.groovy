package com.genre.base.scraper.repo.objects.nhl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.persistence.*

// this table manages the many-many user to subscription relationship
@JsonIgnoreProperties
@Entity
@Table(name="subscription_type")
class SubscriptionTypeVO implements Serializable {

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="subscription_id")
    List<SubscriptionVO> subscriptionVOList

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long subscription_type_id

    @JsonProperty
    String subscriptionName // ie. "Starting Goalie Subscription"

    @JsonProperty
    String subscriptionText // ie. "Subscribe to all goalie updates now!"

    @JsonProperty
    String priceMonthly // price by the month

    @JsonProperty
    String priceSeason // price for a season

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updateTimeStamp

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date createTimeStamp

}

package com.genre.base.scraper.repo.objects.nhl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@JsonIgnoreProperties
@Entity
@Table(name="subscription")
class SubscriptionVO implements Serializable {

    @Id
    @JsonProperty
    Integer subscription_type_id

    @JsonProperty
    String subscriptionName // ie. "Starting Goalie Subscription"

    @JsonProperty
    String subscriptionText // ie. "Subscribe to all goalie updates now!"

    @JsonProperty
    String priceMonthly // price by the month

    @JsonProperty
    String priceSeason // price for a season

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

package com.genre.base.scraper.repo.objects.nhl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@JsonIgnoreProperties
@Entity
@Table(name="user")
class UserVO implements Serializable {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer user_id

    @JsonProperty
    String username

    @JsonProperty
    String password

    @JsonProperty
    String emailAddress

    // this will be used to keep a string list off all the subscriptionIDs for a user '{1,2,3,4,5}'
    // each subscriptionID will map to a subscription to a certain update type (1=starting goalie, 2=starting whatever,etc)
    @JsonProperty
    String subscriptionIDs // these implicitly reference the subscriptionTypeIDs in SubscriptionVO

    // will be used to implement "stay signed in" feature by comparing this token with the one in the browser
    @JsonProperty
    String token

    @JsonProperty
    Integer active // will be toggled to 0 if they have not paid

    @JsonProperty
    Integer trialUser // will be toggled to 1 if a trial user

    @JsonProperty
    Integer isAdmin // will be toggled to 1 if is an admin

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    java.util.Date updateTimeStamp

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    java.util.Date createTimeStamp

}

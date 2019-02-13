package com.genre.base.scraper.repo.objects.nhl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.Table

// this table manages the many-many user to subscription relationship
@JsonIgnoreProperties
@Entity
@Table(name="user_subscription")
@IdClass(CompUserSubKey.class)
class UserSubscriptionVO implements Serializable {

    @Id
    @Column(name="user_subscription_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    Long user_subscription_id

    @JoinColumn(name="user_id",table="user",referencedColumnName="user_id")
    @JsonProperty
    @Id
    Integer user_id

    @JoinColumn(name = "subscription_type_id",table="subscription",referencedColumnName="subscription_type_id")
    @JsonProperty
    @Id
    Integer subscription_type_id

}

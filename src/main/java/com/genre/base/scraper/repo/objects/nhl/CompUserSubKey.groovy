package com.genre.base.scraper.repo.objects.nhl


import javax.persistence.*

class CompUserSubKey implements Serializable {

    @Id
    @Column(name="user_subscription_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long user_subscription_id

    @Id
    Integer user_id

    @Id
    Integer subscription_type_id

}

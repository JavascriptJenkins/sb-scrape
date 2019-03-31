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
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@JsonIgnoreProperties
@Entity
@Table(name="scrape_audit")
class ScrapeAuditVO implements Serializable {

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="scrape_type_id")
    ScrapeTypeVO scrapeTypeVO

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long scrape_audit_id

    @JsonProperty
    boolean success

    String exception

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date start

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date end

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date duration

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updateTimeStamp

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date createTimeStamp



}

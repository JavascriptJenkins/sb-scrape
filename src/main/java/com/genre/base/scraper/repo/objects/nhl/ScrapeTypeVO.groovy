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
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@JsonIgnoreProperties
@Entity
@Table(name="scrape_type")
class ScrapeTypeVO implements Serializable {


    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="scrape_audit_id")
    List<ScrapeAuditVO> scrapeAuditVOList

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long scrape_type_id

    @JsonProperty
    String name

    @JsonProperty
    String description

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updateTimeStamp

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date createTimeStamp


}

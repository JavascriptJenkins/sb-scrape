package com.genre.base.scraper.repo.objects.nhl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.constraints.Size

@JsonIgnoreProperties
@Entity
@Table(name="user")
class UserVO implements Serializable {

    @OneToMany(mappedBy="userVO",cascade=CascadeType.ALL)
    List<SubscriptionVO> subscriptionVOList;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    private Integer user_id;

    @JsonProperty
    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @JsonProperty
    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty
    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String password;

    @JsonProperty
    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;

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

    @JsonProperty
    String role

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    java.util.Date updateTimeStamp

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    java.util.Date createTimeStamp

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}

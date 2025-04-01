package com.travel.to.travel_to.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.travel.to.travel_to.entity.attraction.Attraction;
import com.travel.to.travel_to.entity.attraction.AttractionDiscussion;
import com.travel.to.travel_to.entity.common.UuidAbleTimedEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@MappedSuperclass
public class BaseUser extends UuidAbleTimedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -6403089469548212861L;

    private String password;
    private String email;
    private String name;
    private String surname;
    private String phone;

    @JsonBackReference
    @OneToMany(mappedBy = "owner")
    private List<Attraction> ownerAttractions;
    @OneToMany(mappedBy = "likedBy")
    private List<Attraction> likedAttractions;
    @OneToMany(mappedBy = "author")
    private List<AttractionDiscussion> authorAttractionDiscussions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_to_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    public String getPassword() {
        return password;
    }

    public BaseUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BaseUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public BaseUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public BaseUser setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public BaseUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public List<Attraction> getOwnerAttractions() {
        return ownerAttractions;
    }

    public BaseUser setOwnerAttractions(List<Attraction> ownedAttractions) {
        this.ownerAttractions = ownedAttractions;
        return this;
    }

    public List<Attraction> getLikedAttractions() {
        return likedAttractions;
    }

    public BaseUser setLikedAttractions(List<Attraction> likedAttractions) {
        this.likedAttractions = likedAttractions;
        return this;
    }

    public List<AttractionDiscussion> getAuthorAttractionDiscussions() {
        return authorAttractionDiscussions;
    }

    public BaseUser setAuthorAttractionDiscussions(List<AttractionDiscussion> authorAttractionDiscussions) {
        this.authorAttractionDiscussions = authorAttractionDiscussions;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public BaseUser setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }
}

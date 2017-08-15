package pl.patrykks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookProfile {
    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "birthday")
    private Instant birthday;

    @JsonProperty(value = "firstname")
    private String firstname;

    @JsonProperty(value = "lastname")
    private String lastname;

    @JsonProperty(value = "occupation")
    private String occupation;

    @JsonProperty(value = "gender")
    private Gender gender;

    @JsonProperty(value = "city")
    private City city;

    @JsonProperty(value = "work")
    private String work;

    @JsonProperty(value = "friends")
    private List<String> friends;

    @JsonProperty(value = "school")
    private String school;

    @JsonProperty(value = "location")
    private String location;

    @JsonProperty(value = "relationship")
    private Relationship relationship;

    @JsonProperty(value = "posts")
    private List<Post> posts;

    public FacebookProfile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        FacebookProfile otherProfile = (FacebookProfile) other;

        return getId() != null ? getId().equals(otherProfile.getId()) : otherProfile.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "FacebookProfile{" +
                "id='" + id + '\'' +
                ", birthday=" + birthday +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", occupation='" + occupation + '\'' +
                ", gender=" + gender +
                ", city=" + city +
                ", work='" + work + '\'' +
                ", friends=" + friends +
                ", school='" + school + '\'' +
                ", location='" + location + '\'' +
                ", posts=" + posts +
                '}';
    }
}
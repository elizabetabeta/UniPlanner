package ba.sum.fpmoz.uniplanner.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String firstname;
    public String lastname;
    public String email;
    public String profileImageUrl;

    public User() {
    }

    public User(String firstname, String lastname, String email, String profileImageUrl) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
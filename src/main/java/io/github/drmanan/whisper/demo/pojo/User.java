/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> User </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Monday, 19 of June, 2023; 09:29:01
 */

package io.github.drmanan.whisper.demo.pojo;

import com.esotericsoftware.minlog.Log;
import io.github.drmanan.whisper.util.Utils;

public class User {
    private String username;
    private String email;
    private String telephone;
    private String address;

    public User() {
    }

    public User(String username, String email, String telephone, String address) {
        this.username = username;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        if (!Utils.isNumbers(telephone)){
            Log.info("CAUTION: User: setTelephone: Telephone number in NaN, LoL");
        }
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

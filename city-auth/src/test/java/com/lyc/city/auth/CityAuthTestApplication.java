package com.lyc.city.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author lyc
 * @date 2020/10/12 15:41
 */
public class CityAuthTestApplication {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("secret"));
    }
}

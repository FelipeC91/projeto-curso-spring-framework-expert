package br.com.personalportifolio.brewer.configuration.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MakeValidPassword {
    public static void main(String[] args) {
        var enc = new BCryptPasswordEncoder();
        System.out.println(enc.encode("admin"));
    }
}

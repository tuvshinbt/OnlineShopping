package edu.mum.cs490.project.utils;

import edu.mum.cs490.project.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Erdenebayar on 4/23/2018
 */
public class SignedUser {

    public static User getSignedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }
}

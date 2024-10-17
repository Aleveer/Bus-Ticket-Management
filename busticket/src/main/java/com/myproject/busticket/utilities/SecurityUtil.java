package com.myproject.busticket.utilities;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.myproject.busticket.models.Account;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityUtil {
    public static Account getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Account) {
            return (Account) authentication.getPrincipal();
        }
        return null;
    }

    public static String getCurrentMail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Account) {
            return ((Account) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public static Collection<? extends GrantedAuthority> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Account) {
            return authentication.getAuthorities();
        }
        return null;
    }

    public static boolean hasRole(String role) {
        Collection<? extends GrantedAuthority> authorities = getCurrentUserRoles();
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }
}

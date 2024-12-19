package ch.zhaw.rentmybike.services;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    public boolean userHasRole(String role) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> userRoles = jwt.getClaimAsStringList("user_roles");
        if (userRoles.contains(role)) {
            return true;
        }
        return false;
    }
}

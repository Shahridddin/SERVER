package uz.pdp.simple_l.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //response.sendRedirect("/auth/check-login");
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        if (!customUserDetails.isEnabled()) {
            response.sendRedirect("/auth/register");
        }
        if (customUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("/admin/page-get");
        } else if (customUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            response.sendRedirect("/user/page-get");
        }
    }
}


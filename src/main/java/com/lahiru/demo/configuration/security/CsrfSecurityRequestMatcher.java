package com.lahiru.demo.configuration.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Component
public class CsrfSecurityRequestMatcher implements RequestMatcher {

    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    
    private AntPathRequestMatcher authenticateRequestUri =
            new AntPathRequestMatcher("/api/v1/authenticate");

    @Override
    public boolean matches(HttpServletRequest request) {

        if (allowedMethods.matcher(request.getMethod()).matches()) {
            return false;
        }

        if (acceptedUri(request)) {
            return false;
        }
        return true;
    }

    private boolean acceptedUri(HttpServletRequest request) {
        return authenticateRequestUri.matches(request);
    }
}

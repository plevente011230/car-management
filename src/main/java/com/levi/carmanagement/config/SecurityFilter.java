package com.levi.carmanagement.config;

import com.levi.carmanagement.service.ApplicationState;
import com.levi.carmanagement.service.SecurityService;
import io.jsonwebtoken.*;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;

@Provider
@Secure
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    public static final String BEARER = "Bearer";

    @Inject
    ApplicationState applicationState;

    @Inject
    SecurityService securityService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if(authHeader  == null || !authHeader.startsWith(BEARER)) {
            throw new NotAuthorizedException("No authorization header provided");
        }
        String token = authHeader.substring(BEARER.length()).trim();
        try {
            Key key = securityService.generateKey(applicationState.getUsername());
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            SecurityContext securityContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return () -> Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
                }
                @Override
                public boolean isUserInRole(String role) {
                    return securityContext.isUserInRole(role);
                }
                @Override
                public boolean isSecure() {
                    return securityContext.isSecure();
                }
                @Override
                public String getAuthenticationScheme() {
                    return securityContext.getAuthenticationScheme();
                }
            });
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}

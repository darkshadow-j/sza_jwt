package pl.plenczewski.homejwt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.security.rsa.RSAKeyFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collections;


public class JWTFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken authenticationToken = getUsernamePasswordAuthenticationToken(authorization);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String authorization) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512("Zr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeThWmYq3t6w9z$C&F)J@NcRfU")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(authorization.substring(7));
        String name = decodedJWT.getClaim("name").asString();
        boolean isAdmin = decodedJWT.getClaim("admin").asBoolean();
        String role = "ROLE_USER";
        if(isAdmin) {
            role = "ROLE_ADMIN";
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(name, null, Collections.singleton(authority));
    return token;
    }

}

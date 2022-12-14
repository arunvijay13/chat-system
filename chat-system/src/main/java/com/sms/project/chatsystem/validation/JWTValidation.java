package com.sms.project.chatsystem.validation;

import com.sms.project.chatsystem.constant.APPConstants;
import com.sms.project.chatsystem.exception.InvalidJwtTokenException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class JWTValidation {

    public static void validateToken(String token)  {
        try {
            Jwts.parser().setSigningKey(APPConstants.SECRET_KEY).parseClaimsJws(token);
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new SignatureException("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new MalformedJwtException("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new InvalidJwtTokenException("JWT token is expired: " +  e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new UnsupportedJwtException("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new IllegalArgumentException("WT claims string is empty: " + e.getMessage());
        }
    }

    public static Map<String, Object> getHeader() {
        Map<String, Object> headers = new LinkedHashMap<>();
        headers.put("alg", "HS256");
        headers.put("type", "JWT");

        return headers;
    }
}

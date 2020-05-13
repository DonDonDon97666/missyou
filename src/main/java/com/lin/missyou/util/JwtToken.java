package com.lin.missyou.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/23
 */
@Component
public class JwtToken {

    private static String jwtKey;
    private static Long expireTimeIn;
    private static Integer defaultScope = 8;

    @Value("${missyou.security.jwt-key}")
    public void setJwtKey(String jwtKey) {
        JwtToken.jwtKey = jwtKey;
    }

    @Value("${missyou.security.token-expired-in}")
    public void setExpireTimeIn(Long expireTimeIn) {
        JwtToken.expireTimeIn = expireTimeIn;
    }

    public static Optional<Map<String, Claim>> getClaims(String token) {
        DecodedJWT decodedJWT;
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(decodedJWT.getClaims());
    }

    public static Boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public static String makeToken(Long uid, Integer scope) {
        return JwtToken.getToken(uid, scope);
    }

    public static String makeToken(Long uid) {
        return JwtToken.getToken(uid, JwtToken.defaultScope);
    }

    private static String getToken(Long uid, Integer scope) {
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);

        Map<String, Date> dateMap = JwtToken.calculateExpireIssue();

        String token = JWT.create()
                .withClaim("uid", uid)
                .withClaim("scope", scope)
                .withExpiresAt(dateMap.get("expireTime"))
                .withIssuedAt(dateMap.get("now"))
                .sign(algorithm);

        return token;
    }

    private static Map<String, Date> calculateExpireIssue() {
        Map<String, Date> map = new HashMap<>();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusSeconds(JwtToken.expireTimeIn);

        Date nowDate = DateUtils.asDate(now);
        Date expireDate = DateUtils.asDate(expireTime);

        map.put("now", nowDate);
        map.put("expireTime", expireDate);

        return map;
    }
}

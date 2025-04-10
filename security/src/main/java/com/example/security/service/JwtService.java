package com.example.security.service;

import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.security.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	public String genarateTocken(User user) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().claims().add(claims).subject(user.getUsername()).issuer("deepa")
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 10 * 1000)).and().signWith(genarateKey())
				.compact();

	}
	
	public SecretKey genarateKey() {
		byte[] decode=Decoders.BASE64.decode(getSecretkey());
		return Keys.hmacShaKeyFor(decode);
		
	}

	public String getSecretkey() {
		return "RqxPOuVfHoBA8Uq40MhJvfY6qEHOOWWvg6N9W9vt23s=";
	}

	public String extractUserName(String tocken) {
		return extractClaims(tocken,Claims::getSubject);
	}

	private <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }
	private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(genarateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
	public boolean isValidTocken(String tocken, UserDetails userDetails) {
		final String userName = extractUserName(tocken);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(tocken));	
	}

	private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

}

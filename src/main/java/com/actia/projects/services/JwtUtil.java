package com.actia.projects.services;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.actia.projects.dto.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;




@Service
public class JwtUtil {
	
	private final  String  secretKey = "926D96C90030DD58429D2751AC1BDBBC";
	   private final Random RANDOM = new SecureRandom();


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", userDto.getFirstName() + " " + userDto.getLastName());
        claims.put("role", userDto.getRole() );
        return createToken(claims, userDto.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {
    	
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60*60*24 ))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }


    public String generateTokenByEmail (String email){
	      
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() +10000*60 ))
				.signWith(SignatureAlgorithm.HS512, secretKey )
				.compact();
	   
   }
    
    public String generateStringId(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(secretKey.charAt(RANDOM.nextInt(secretKey.length())));
        }

        return new String(returnValue);
    }

}

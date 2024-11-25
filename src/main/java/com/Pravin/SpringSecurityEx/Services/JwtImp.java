package com.Pravin.SpringSecurityEx.Services;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import  java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtImp  {

    private String secretkey="";

    public JwtImp(){
    try {
        KeyGenerator keyGn= KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk=keyGn.generateKey();
        secretkey=Base64.getEncoder().encodeToString(sk.getEncoded());
       
    } catch (NoSuchAlgorithmException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }

   
    
       
        public String generateToken(String username) {
            Map<String ,Object> claims=new HashMap<>();
            return Jwts
            .builder()
            .claims()
            .add(claims)
            .subject(username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+60*60*30))
            .and()
            .signWith(getKey())
            .compact();
        }

        private SecretKey getKey() {
            byte[] keyBytes=Decoders.BASE64.decode(secretkey);
            return Keys.hmacShaKeyFor(keyBytes);
           }
        
        private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
            final Claims claims = extractAllClaims(token);
            return claimResolver.apply(claims);
        }
        private Claims extractAllClaims(String token) {
            return Jwts.parser().setSigningKey(getKey()) .build() .parseClaimsJws(token) .getBody();
        }
        public boolean validateToken(String token, UserDetails userDetails) {
            final String userName = extractUSername(token);
            System.out.println(userName);
            System.out.println(userDetails.getUsername());
            if (userName.equals(userDetails.getUsername()) && !isTokenExpired(token)) {
                System.out.println("true");
                return true;
               
            }
            System.out.println("fals");
              return false;
        }

        private boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }
    
        private Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }



      
        public String extractUSername(String token) {
            return extractClaim(token, Claims::getSubject);
        }

}

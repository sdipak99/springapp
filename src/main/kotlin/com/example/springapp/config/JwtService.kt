package com.example.springapp.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtService {
    val SECRET_KEY = "6c4d62bec94a4ac51ea5d61b7f67bc9dc1f992b46c7a0f25832fd1320148e43a"

    fun extractUsername(token: String): String? {
        return extractClaim(token,Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractClaims(token)
        return claimsResolver(claims)
    }

    fun extractClaims(jwt: String):Claims{
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(jwt)
            .body
    }

    private fun getSignInKey(): Key {
        val keyByte = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyByte)
    }

    fun generateJwt(userDetails: UserDetails):String{
        return Jwts
            .builder()
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000*60*24))
            .signWith(getSignInKey(),SignatureAlgorithm.HS256)
            .compact()
    }

    fun isJwtValid(jwt:String,userDetails: UserDetails):Boolean{
        val userName = extractUsername(jwt)
        return userName == userDetails.username && isJwtActive(jwt)
    }



    private fun isJwtActive(jwt: String): Boolean {
        return extractClaim(jwt,Claims::getExpiration).after(Date())
    }
}

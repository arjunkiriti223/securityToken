package in.nit.util;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${app.secret}")
	private String secret;
	
	//6 validate username and TokenExp
	public boolean validateToken(String username,String token) {
		String tokenuser=getUserName(token);
		//user must match and token not expaire

		return (tokenuser.equals(username))||isTokenExp(token);
	}
	
	//5 token expairation check
	public boolean isTokenExp(String token) {
		return getExpDate(token).before(new Date(System.currentTimeMillis()));
	}
	//4 read expairation
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	//3 read userName(subject)
	public String getUserName(String token) {
		return getClaims(token).getSubject();
		
	}
	
		//2 get Claims
	public Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(Base64.getEncoder().encode(secret.getBytes()))
				.parseClaimsJws(token)
				.getBody();
	}
	
	
		//1 generate Token using Subject
		public String generateToken(String subject) {
			return Jwts.builder()
					.setSubject(subject)
					.setIssuer("ktr")
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(5)))
					.signWith(SignatureAlgorithm.HS256,Base64.getEncoder().encode(secret.getBytes()))
					.compact();
		}

}

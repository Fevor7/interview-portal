package by.interview.portal.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import by.interview.portal.Constant.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	public String getloginFomToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(JwtConstant.SIGNING_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private Date calculateExpirationDate(Date createdDate) {
		return new Date(createdDate.getTime() + 10000 * 1000);
	}

	private String doGenerateToken(Map<String, Object> claims, String username) {
		final Date createdDate = new Date();
		final Date expirationDate = calculateExpirationDate(createdDate);

		System.out.println("doGenerateToken " + createdDate);

		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(createdDate)
				.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, JwtConstant.SIGNING_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {

		final String login = getloginFomToken(token);
		final Date created = getIssuedAtDateFromToken(token);
		return login.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
}

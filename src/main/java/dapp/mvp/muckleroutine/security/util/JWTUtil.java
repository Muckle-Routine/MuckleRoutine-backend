package dapp.mvp.muckleroutine.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

@Log4j2
public class JWTUtil {
	//1month
	private long expire = 50 * 24 * 30;
	private String secretKey = "muckle_Routine";
	
//	private String generatePublicKey() {
//		// secretKey logic 생성.
//		Date date = new Date();
//		secretKey = date + "muckle_Routine" + date;
//
//		return secretKey;
//	}
	
	public String generateToken(String content) throws Exception{
		
		return Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
				.claim("sub", content)
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))
				.compact();
	}
	
	public String validateAndExtract(String tokenStr){
		
		String contentValue = null;
		
		try {
			DefaultJws defaultJws = (DefaultJws) Jwts.parser()
					.setSigningKey(secretKey.getBytes("UTF-8")).parseClaimsJws(tokenStr);
			

			log.info("-------- validateAndExtract -----------");
			log.info(defaultJws);
			
			log.info(defaultJws.getBody().getClass());
			
			DefaultClaims claims = (DefaultClaims) defaultJws.getBody();
			
			log.info("---------------------" + claims);
			
			contentValue = claims.getSubject();
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			contentValue = null;
		}
		return contentValue;
	}
}

package dapp.mvp.muckleroutine.security;

import dapp.mvp.muckleroutine.dto.UserDTO;
import dapp.mvp.muckleroutine.security.dto.AppAuthUserDTO;
import dapp.mvp.muckleroutine.security.service.AppUserDetailService;
import dapp.mvp.muckleroutine.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter{
	
	private AntPathMatcher antPathMatcher;
	private Map<String, String> patterns;
	private JWTUtil jwtUtil;
	

	@Autowired
	private AppUserDetailService userDetailService;
	
	
	public ApiCheckFilter(Map<String, String> patterns, JWTUtil jwtUtil) {
		this.antPathMatcher = new AntPathMatcher();
		this.patterns = patterns;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
		
		log.info("REQUESTURI: " + request.getRequestURI());
		
		boolean antPathMatch = false;
		String role = null;
		for(String pattern : patterns.keySet()) {
			if(antPathMatcher.match(pattern, request.getRequestURI())) {
				antPathMatch = true;
				role = patterns.get(pattern).toString();
				log.info(antPathMatcher.match(pattern, request.getRequestURI()));
				break;
			}
		}
		
		if(antPathMatch) {
			String message = "";
			log.info("ApiCheckFilter.......................");
			
			Map<String, Boolean> check = checkAuthHeader(request, role);
			boolean checkHeader = check.get("checkHeader"); //Authorization Header 유효성 확인
			boolean checkRole = check.get("checkRole"); //권한확인
			
			if(checkHeader && checkRole) {
				filterChain.doFilter(request, response);
				return;
			}
			if(!checkHeader){//실패시 json 데이터 리턴. AuthenticationManager를 사용하는 방법도 있음
				message = "FAIL CHECK API TOKEN";
			}else if(!checkRole) {
				message = "NEED " + role + "_AUTH";
			}
			
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			// json 리턴 및 한글깨짐 수정.
			response.setContentType("application/json;charset=utf-8");
			JSONObject json = new JSONObject();
			json.put("code", "403");
			json.put("message", message);
			
			PrintWriter out = response.getWriter();
			out.print(json);
			return;
		}
		
		filterChain.doFilter(request, response); //다음 필터의 단계로 넘어가는 역할. Security Config를 통해 스프링의 빈으로 설정
	}
	
	private Map<String, Boolean> checkAuthHeader(HttpServletRequest request, String role) {
		Map<String, Boolean> check = new HashMap<>();
		check.put("checkHeader", false);
		check.put("checkRole", false);
		
		String authHeader = request.getHeader("Authorization");
		
		if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) { // JWT이용할 때 Bearer 사용
			log.info("Authorization exist: " + authHeader);
			
			try {
				String walletAddress = jwtUtil.validateAndExtract(authHeader.substring(7));
				log.info("validate result: " + walletAddress);
				
				UserDetails uDetail = userDetailService.loadUserByUsername(walletAddress);
				check.put("checkHeader", walletAddress.length() > 0);
				check.put("checkRole", uDetail.getAuthorities().toString().contains(role));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return check;
	}
}

package dapp.mvp.muckleroutine.security;

import dapp.mvp.muckleroutine.entity.AppUser;
import dapp.mvp.muckleroutine.entity.KlipRequest;
import dapp.mvp.muckleroutine.security.dto.AppAuthUserDTO;
import dapp.mvp.muckleroutine.security.util.JWTUtil;
import dapp.mvp.muckleroutine.service.KlipRequestService;
import dapp.mvp.muckleroutine.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter{
	
	private JWTUtil jwtUtil;

	@Autowired
	private KlipRequestService klipRequestService;
	@Autowired
	private UserService userService;

	public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil) {
		super(defaultFilterProcessesUrl);
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestNo = request.getParameter("requestNo");
		if(requestNo != null) {
			KlipRequest klipRequest = klipRequestService.getSuccessRequest(requestNo);
			if (klipRequest != null) {
				AppUser appUser = userService.getAuthedUser(klipRequest);
				if (appUser != null) {
					UsernamePasswordAuthenticationToken authToken =
							new UsernamePasswordAuthenticationToken(appUser.getWalletAddress(), requestNo); //사용자 로그인 인증

					return getAuthenticationManager().authenticate(authToken);
				}
			}
		}
		throw new AuthenticationServiceException("not valid request.");
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException{
		
		//email address
		String walletAddress = ((AppAuthUserDTO)authResult.getPrincipal()).getUsername();
		
		String token = null;
		try {
			token = jwtUtil.generateToken(walletAddress);
			
			response.setContentType("text/plain");
			response.getOutputStream().write(token.getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

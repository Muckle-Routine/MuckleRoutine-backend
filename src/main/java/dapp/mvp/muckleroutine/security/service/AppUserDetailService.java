package dapp.mvp.muckleroutine.security.service;



import dapp.mvp.muckleroutine.dto.UserDTO;
import dapp.mvp.muckleroutine.entity.AppUser;
import dapp.mvp.muckleroutine.repository.AppUserRepository;
import dapp.mvp.muckleroutine.repository.KlipRequestRepository;
import dapp.mvp.muckleroutine.security.dto.AppAuthUserDTO;
import dapp.mvp.muckleroutine.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor // repository 주입성 추가
public class AppUserDetailService implements UserDetailsService{
	
	private final AppUserRepository appUserRepository; //주입성 추가
	private final UserService userservice;

	private final KlipRequestRepository klipRequestRepository;

	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String walletAddress) throws UsernameNotFoundException{ //사용자가 존재하지 않으면 Exception 처리
		log.info("AppUserDetailService loadUserByUsername " + walletAddress);
		Optional<AppUser> result = appUserRepository.findById(walletAddress);

		if(result.isEmpty()) {
			throw new UsernameNotFoundException("Check Request No");
		}

		AppUser appUser = result.get();
		UserDTO userDTO = userservice.entityToDTO(appUser);

		AppAuthUserDTO appAuthUser = new AppAuthUserDTO( // UserDetails 타입으로 처리하기 위해서 타입 변
				userDTO,
				passwordEncoder.encode(userDTO.getRequest().getRequest()),
				Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")) //접두어 사용해 스프링 시큐리티에서 사용하는 SimpleGrantedAuthority로 변
		);

		return appAuthUser;
	}



}

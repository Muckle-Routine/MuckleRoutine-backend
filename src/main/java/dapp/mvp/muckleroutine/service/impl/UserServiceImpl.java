package dapp.mvp.muckleroutine.service.impl;

import dapp.mvp.muckleroutine.dto.CertificationDTO;
import dapp.mvp.muckleroutine.entity.*;
import dapp.mvp.muckleroutine.repository.AppUserRepository;
import dapp.mvp.muckleroutine.repository.BoardRepository;
import dapp.mvp.muckleroutine.repository.CertificationRepository;
import dapp.mvp.muckleroutine.repository.RoutineRepository;
import dapp.mvp.muckleroutine.security.util.JWTUtil;
import dapp.mvp.muckleroutine.service.CertificationService;
import dapp.mvp.muckleroutine.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AppUserRepository appUserRepository;

    @Override
    public AppUser getAuthedUser(KlipRequest klipRequest){
        Optional<AppUser> optionalAppUser = appUserRepository.findByRequest(klipRequest);
        if(optionalAppUser.isPresent()){
            return optionalAppUser.get();
        }
        return null;
    }

    @Override
    public AppUser get(HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            JWTUtil jwtUtil = new JWTUtil();
            String walletAddress = jwtUtil.validateAndExtract(authHeader.substring(7));
            Optional<AppUser> user = appUserRepository.findById(walletAddress);
            if(user.isPresent()) {
                return user.get();
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    public AppUser save(AppUser appUser){
        return appUserRepository.save(appUser);
    }

}

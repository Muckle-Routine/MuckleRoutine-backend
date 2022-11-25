package dapp.mvp.muckleroutine.service.impl;

import dapp.mvp.muckleroutine.dto.CertificationDTO;
import dapp.mvp.muckleroutine.entity.*;
import dapp.mvp.muckleroutine.repository.AppUserRepository;
import dapp.mvp.muckleroutine.repository.BoardRepository;
import dapp.mvp.muckleroutine.repository.CertificationRepository;
import dapp.mvp.muckleroutine.repository.RoutineRepository;
import dapp.mvp.muckleroutine.service.CertificationService;
import dapp.mvp.muckleroutine.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AppUserRepository appUserRepository;
    public AppUser getAuthedUser(KlipRequest klipRequest){
        Optional<AppUser> optionalAppUser = appUserRepository.findByRequest(klipRequest);
        if(optionalAppUser.isPresent()){
            return optionalAppUser.get();
        }
        return null;
    }

    public AppUser save(AppUser appUser){
        return appUserRepository.save(appUser);
    }

}

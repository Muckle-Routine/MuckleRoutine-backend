package dapp.mvp.muckleroutine.service;

import dapp.mvp.muckleroutine.dto.CertificationDTO;
import dapp.mvp.muckleroutine.dto.RoutineDTO;
import dapp.mvp.muckleroutine.dto.UserDTO;
import dapp.mvp.muckleroutine.entity.AppUser;
import dapp.mvp.muckleroutine.entity.Certification;
import dapp.mvp.muckleroutine.entity.KlipRequest;
import dapp.mvp.muckleroutine.entity.Routine;

import java.util.List;

public interface UserService {
    AppUser getAuthedUser(KlipRequest klipRequest);

    AppUser save(AppUser appUser);

    default AppUser dtoToEntity(UserDTO userDTO){
        AppUser appUser = AppUser.builder()
                .walletAddress(userDTO.getWalletAddress())
                .request(userDTO.getRequest())
                .participatedRoutine(userDTO.getParticipatedRoutine())
                .build();
        return appUser;
    }
    default UserDTO entityToDTO(AppUser appUser){
        UserDTO userDTO = UserDTO.builder()
                .walletAddress(appUser.getWalletAddress())
                .request(appUser.getRequest())
                .participatedRoutine(appUser.getParticipatedRoutine())
                .build();
        return userDTO;
    }


}

package dapp.mvp.muckleroutine.service;

import dapp.mvp.muckleroutine.dto.CertificationDTO;
import dapp.mvp.muckleroutine.dto.RoutineDTO;
import dapp.mvp.muckleroutine.dto.UserDTO;
import dapp.mvp.muckleroutine.entity.AppUser;
import dapp.mvp.muckleroutine.entity.Certification;
import dapp.mvp.muckleroutine.entity.KlipRequest;
import dapp.mvp.muckleroutine.entity.Routine;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    AppUser get(HttpServletRequest request) throws Exception; //api 요청(request)에 따른 사용자 정보 조회
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

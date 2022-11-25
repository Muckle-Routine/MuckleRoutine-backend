package dapp.mvp.muckleroutine.service;

import dapp.mvp.muckleroutine.dto.KlipRequestDTO;
import dapp.mvp.muckleroutine.dto.UserDTO;
import dapp.mvp.muckleroutine.entity.AppUser;
import dapp.mvp.muckleroutine.entity.KlipRequest;
import dapp.mvp.muckleroutine.entity.KlipRequestType;
import dapp.mvp.muckleroutine.entity.Routine;

public interface KlipRequestService {
    KlipRequest getSuccessRequest(String request);

    KlipRequest save(KlipRequest klipRequest);
    KlipRequest get(Long requestNo);
    void checkRequest(KlipRequestType requestType, KlipRequest klipRequest) throws InterruptedException;
    void delete(KlipRequest klipRequest);
    default KlipRequest dtoToEntity(KlipRequestDTO klipRequestDTO){
        KlipRequest klipRequest = KlipRequest.builder()
                .request(klipRequestDTO.getRequest())
                .requestExpiredTime(klipRequestDTO.getRequestExpiredTime())
                .type(klipRequestDTO.getType())
                .status(klipRequestDTO.getStatus())
                .build();
        return klipRequest;
    }
    default KlipRequestDTO entityToDTO(KlipRequest klipRequest){
        KlipRequestDTO klipRequestDTO = KlipRequestDTO.builder()
                .request(klipRequest.getRequest())
                .requestExpiredTime(klipRequest.getRequestExpiredTime())
                .type(klipRequest.getType())
                .status(klipRequest.getStatus())
                .build();
        return klipRequestDTO;
    }


}

package dapp.mvp.muckleroutine.service;

import dapp.mvp.muckleroutine.dto.CertificationDTO;
import dapp.mvp.muckleroutine.entity.Certification;
import dapp.mvp.muckleroutine.entity.CertificationStatus;

public interface CertificationService {
    Certification save(CertificationDTO certification);

    Certification getOne(Long routineNo);
    Certification getForVote();

    void vote(Long certificationNo, CertificationStatus status, String failReason);

    default Certification dtoToEntity(CertificationDTO certificationDTO){
        Certification certification = Certification.builder()
                .certification(certificationDTO.getCertification())
                .certificationDate(certificationDTO.getCertificationDate())
                .failCount(certificationDTO.getFailCount())
                .failReasons(certificationDTO.getFailReasons())
                .image(certificationDTO.getImage())
                .no(certificationDTO.getNo())
                .routine(certificationDTO.getRoutine())
                .status(certificationDTO.getStatus())
                .successCount(certificationDTO.getSuccessCount())
                .voteCount(certificationDTO.getVoteCount())
                .build();
        return certification;
    }
    default CertificationDTO entityToDTO(Certification certification){
        CertificationDTO certificationDTO = CertificationDTO.builder()
                .certification(certification.getCertification())
                .certificationDate(certification.getCertificationDate())
                .failCount(certification.getFailCount())
                .failReasons(certification.getFailReasons())
                .image(certification.getImage())
                .no(certification.getNo())
                .routine(certification.getRoutine())
                .status(certification.getStatus())
                .successCount(certification.getSuccessCount())
                .voteCount(certification.getVoteCount())
                .build();
        return certificationDTO;
    }
}

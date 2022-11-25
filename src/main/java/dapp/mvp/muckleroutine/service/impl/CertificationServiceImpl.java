package dapp.mvp.muckleroutine.service.impl;

import dapp.mvp.muckleroutine.dto.CertificationDTO;
import dapp.mvp.muckleroutine.entity.Board;
import dapp.mvp.muckleroutine.entity.Certification;
import dapp.mvp.muckleroutine.entity.CertificationStatus;
import dapp.mvp.muckleroutine.entity.Routine;
import dapp.mvp.muckleroutine.repository.BoardRepository;
import dapp.mvp.muckleroutine.repository.CertificationRepository;
import dapp.mvp.muckleroutine.repository.RoutineRepository;
import dapp.mvp.muckleroutine.service.CertificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {
    private final CertificationRepository certificationRepository;
    private final RoutineRepository routineRepository;
    private final BoardRepository boardRepository;

    @Override
    public Certification save(CertificationDTO certificationDTO){
        return certificationRepository.save(dtoToEntity(certificationDTO));
    }

    @Override
    public Certification getOne(Long routineNo){
        Optional<Routine> result = routineRepository.findById(routineNo);

        if(result.isPresent()) {
            Optional<Certification> certification = certificationRepository.findByRoutine(result.get());
            if(certification.isPresent())
                return certification.get();
        }

        return null;
    }
    @Override
    public Certification getForVote(){
        Optional<Certification> result = certificationRepository.findTopByStatusOrderByCertificationDateDesc(CertificationStatus.PROCESS);
        if(result.isPresent())
            return result.get();

        return null;
    }

    @Override
    public void vote(Long certificationNo, CertificationStatus status, String failReason){
        Optional<Certification> result = certificationRepository.findById(certificationNo);
        if(result.isPresent()){
            Certification certification = result.get();
            if(status.equals(CertificationStatus.FAIL)){
                certification.addFailCount();
                Board board = boardRepository.save(Board.builder().contents(failReason).build());
                certification.addFailReason(board);
            }else if(status.equals(CertificationStatus.SUCCESS)){
                certification.addSuccessCount();
            }
            if(certification.getVoteCount() >= 3 && certification.getVoteCount()%2 == 1){ // TODO 추후에는 날짜기준으로 변경
                if(certification.getSuccessCount() > certification.getFailCount()){
                    //성공
                    certification.setStatus(CertificationStatus.SUCCESS); //TODO smart contract 실행
                }else{
                    certification.setStatus(CertificationStatus.FAIL);
                }
            }

            certificationRepository.save(certification);
        }
    }
}

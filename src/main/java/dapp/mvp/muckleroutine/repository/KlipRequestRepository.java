package dapp.mvp.muckleroutine.repository;

import dapp.mvp.muckleroutine.entity.Certification;
import dapp.mvp.muckleroutine.entity.CertificationStatus;
import dapp.mvp.muckleroutine.entity.KlipRequest;
import dapp.mvp.muckleroutine.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KlipRequestRepository extends JpaRepository<KlipRequest, Long> {
    Optional<KlipRequest> findTopByRequestOrderByRequestExpiredTimeDesc(String request);
}

package dapp.mvp.muckleroutine.repository;

import dapp.mvp.muckleroutine.entity.Certification;
import dapp.mvp.muckleroutine.entity.CertificationStatus;
import dapp.mvp.muckleroutine.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    Optional<Certification> findByRoutine(Routine routine);

    List<Certification> findTop20ByStatusOrderByCertificationDateDesc(CertificationStatus certificationStatus);
}

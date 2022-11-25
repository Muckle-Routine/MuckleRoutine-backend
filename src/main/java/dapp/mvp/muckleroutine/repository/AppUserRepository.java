package dapp.mvp.muckleroutine.repository;

import dapp.mvp.muckleroutine.entity.AppUser;
import dapp.mvp.muckleroutine.entity.Board;
import dapp.mvp.muckleroutine.entity.KlipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByRequest(KlipRequest klipRequest);
}

package dapp.mvp.muckleroutine.repository;

import dapp.mvp.muckleroutine.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    List<Routine> findByCategory(String category);
}

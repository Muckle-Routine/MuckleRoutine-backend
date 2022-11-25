package dapp.mvp.muckleroutine.service.impl;

import dapp.mvp.muckleroutine.dto.RoutineDTO;
import dapp.mvp.muckleroutine.entity.Routine;
import dapp.mvp.muckleroutine.repository.RoutineRepository;
import dapp.mvp.muckleroutine.service.RoutineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {
    private final RoutineRepository routineRepository;

    @Override
    public Routine save(RoutineDTO routineDTO) {
        return routineRepository.save(dtoToEntity(routineDTO));
    }

    @Override
    public List<Routine> getList(String category) {
        if(category == null || category.toUpperCase().equals("ALL")){
            return routineRepository.findAll();
        }else{
            return routineRepository.findByCategory(category);
        }
    }

    @Override
    public Routine getOne(Long RoutineNo){
        Optional<Routine> result = routineRepository.findById(RoutineNo);

        if(result.isPresent()) {
            return result.get();
        }

        return null;
    }
}

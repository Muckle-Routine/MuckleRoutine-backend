package dapp.mvp.muckleroutine.service;

import dapp.mvp.muckleroutine.dto.RoutineDTO;
import dapp.mvp.muckleroutine.entity.Routine;

import java.util.List;

public interface RoutineService {
    Routine save(RoutineDTO routine);

    List<Routine> getList(String category);
    Routine getOne(Long RoutineNo);

    default Routine dtoToEntity(RoutineDTO routineDTO){
        Routine routine = Routine.builder()
                .no(routineDTO.getNo())
                .category(routineDTO.getCategory())
                .description(routineDTO.getDescription())
                .endDate(routineDTO.getEndDate())
                .image1(routineDTO.getImage1())
                .image2(routineDTO.getImage2())
                .fee(routineDTO.getFee())
                .participates(routineDTO.getParticipates())
                .skeleton(routineDTO.getSkeleton())
                .startDate(routineDTO.getStartDate())
                .term(routineDTO.getTerm())
                .title(routineDTO.getTitle())
                .build();
        return routine;
    }

    default RoutineDTO entityToDTO(Routine routine){
            RoutineDTO routineDTO = RoutineDTO.builder()
                    .no(routine.getNo())
                    .category(routine.getCategory())
                    .description(routine.getDescription())
                    .endDate(routine.getEndDate())
                    .image1(routine.getImage1())
                    .image2(routine.getImage2())
                    .fee(routine.getFee())
                    .participates(routine.getParticipates())
                    .skeleton(routine.getSkeleton())
                    .startDate(routine.getStartDate())
                    .term(routine.getTerm())
                    .title(routine.getTitle())
                    .build();
            return routineDTO;
        }
}

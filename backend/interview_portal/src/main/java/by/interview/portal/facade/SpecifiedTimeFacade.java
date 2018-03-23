package by.interview.portal.facade;

import java.time.LocalDateTime;
import java.util.List;

import by.interview.portal.dto.SpecifiedTimeDTO;

public interface SpecifiedTimeFacade {

    List<SpecifiedTimeDTO> findAllInRange(LocalDateTime rangeStart, LocalDateTime rangeEnd,
            Long disciplineId);

    List<SpecifiedTimeDTO> findAllForUserInRange(LocalDateTime rangeStart, LocalDateTime rangeEnd);
}

package dh.scheduler.repository;

import dh.scheduler.dto.ScheduleResponseDto;
import dh.scheduler.entity.Author;
import dh.scheduler.entity.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);

    Author saveAuthor(Author author);

    Schedule findScheduleById(Long id);

    Author findAuthorById(Long id);

    List<ScheduleResponseDto> findScheduleListByDate(String date, Pageable pageable);

    List<ScheduleResponseDto> findScheduleListByName(Long id, Pageable pageable);

    List<ScheduleResponseDto> findScheduleListByNameWithDate(String date, Long id, Pageable pageable);

    List<ScheduleResponseDto> findAllScheduleLists(Pageable pageable);

    int updateSchedule(Long id, String contents, LocalDateTime now);

    int updateAuthorName(Long id, String name, LocalDateTime now);

    int deleteSchedule(Long id);

}

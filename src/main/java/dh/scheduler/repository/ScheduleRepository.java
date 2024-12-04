package dh.scheduler.repository;

import dh.scheduler.dto.ScheduleResponseDto;
import dh.scheduler.entity.Author;
import dh.scheduler.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);

    Author saveAuthor(Author author);

    Schedule findScheduleById(Long id);

    Author findAuthorById(Long id);

    List<ScheduleResponseDto> findScheduleListByDate(String date);

    List<ScheduleResponseDto> findScheduleListByName(String name);

    List<ScheduleResponseDto> findScheduleListByNameWithDate(String date, String name);

    List<ScheduleResponseDto> findAllScheduleLists();

    int updateSchedule(Long id, String contents, LocalDateTime now);

    int updateAuthor(Long id, String name);

    int deleteSchedule(Long id);

    int deleteSchedule(Long id);

}

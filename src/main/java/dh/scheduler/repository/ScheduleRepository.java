package dh.scheduler.repository;

import dh.scheduler.dto.ScheduleResponseDto;
import dh.scheduler.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    Schedule findScheduleById(Long id);

    List<ScheduleResponseDto> findScheduleListByDate(String date);

    List<ScheduleResponseDto> findScheduleListByName(String name);

    List<ScheduleResponseDto> findScheduleListByNameWithDate(String date, String name);

    List<ScheduleResponseDto> findAllScheduleLists();

    int updateSchedule(Long id, String name, String contents, LocalDateTime now);

}

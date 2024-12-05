package dh.scheduler.service;

import dh.scheduler.dto.ScheduleRequestDto;
import dh.scheduler.dto.ScheduleResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    ScheduleResponseDto findScheduleById(Long id);

    List<ScheduleResponseDto> findListSchedules(String date, Long id, Pageable pageable);

    ScheduleResponseDto updateSchedule(Long id, String name, String contents, String password);

    void deleteSchedule(Long id, String password);
}

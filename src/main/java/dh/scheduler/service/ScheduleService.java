package dh.scheduler.service;

import dh.scheduler.dto.ScheduleRequestDto;
import dh.scheduler.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    ScheduleResponseDto findScheduleById(Long id);

    List<ScheduleResponseDto> findListSchedules(String date, Long id);

    ScheduleResponseDto updateSchedule(Long id, String name, String contents, String password);

    void deleteSchedule(Long id);
}

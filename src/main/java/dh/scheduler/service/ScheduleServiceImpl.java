package dh.scheduler.service;

import dh.scheduler.dto.ScheduleRequestDto;
import dh.scheduler.dto.ScheduleResponseDto;
import dh.scheduler.entity.Schedule;
import dh.scheduler.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {

        Schedule schedule = new Schedule(
                requestDto.getUserName(),
                requestDto.getTitle(),
                requestDto.getPassword(),
                requestDto.getContents()
                );

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule schedule = scheduleRepository.findScheduleById(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findListSchedules(String date, String name) {

        List<ScheduleResponseDto> findList = new ArrayList<>();

        if(name == null || date == null) {
            findList = scheduleRepository.findAllScheduleLists();
        }

        if(name != null || date != null) {
            findList = scheduleRepository.findScheduleListByNameWithDate(date, name);
        }

        if (name != null) {
            findList = scheduleRepository.findScheduleListByName(name);
        }

        if (date != null) {
            findList = scheduleRepository.findScheduleListByDate(date);
        }

        return findList;
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String name, String contents, String password) {

        if (!password.equals(scheduleRepository.findScheduleById(id).getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀립니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        scheduleRepository.updateSchedule(id, name, contents, now);
        Schedule schedule = scheduleRepository.findScheduleById(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {

        int deleteRow = scheduleRepository.deleteSchedule(id);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id를 찾을 수 없습니다.");
        }
    }


}

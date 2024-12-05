package dh.scheduler.service;

import dh.scheduler.dto.ScheduleRequestDto;
import dh.scheduler.dto.ScheduleResponseDto;
import dh.scheduler.entity.Author;
import dh.scheduler.entity.Schedule;
import dh.scheduler.repository.ScheduleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
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

        Author author = scheduleRepository.findAuthorById(requestDto.getAuthorId());

        Schedule schedule = new Schedule(
                author.getId(),
                requestDto.getTitle(),
                requestDto.getPassword(),
                requestDto.getContents()
        );

        schedule = scheduleRepository.saveSchedule(schedule);

        return new ScheduleResponseDto(schedule, author);
    }


    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule schedule = scheduleRepository.findScheduleById(id);
        Author author = scheduleRepository.findAuthorById(schedule.getAuthorId());

        return new ScheduleResponseDto(schedule, author);
    }


    @Override
    public List<ScheduleResponseDto> findListSchedules(String date, Long id, Pageable pageable) {

        List<ScheduleResponseDto> findList = new ArrayList<>();

        if(id == null && date == null) {
            findList = scheduleRepository.findAllScheduleLists(pageable);
        }

        if(id != null && date != null) {
            findList = scheduleRepository.findScheduleListByNameWithDate(date, id, pageable);
        }

        if (id != null) {
            findList = scheduleRepository.findScheduleListByName(id, pageable);
        }

        if (date != null) {
            findList = scheduleRepository.findScheduleListByDate(date, pageable);
        }

        return findList;
    }


    @Override
    public ScheduleResponseDto updateSchedule(Long id, String userName, String contents, String password) {

        if (!password.equals(scheduleRepository.findScheduleById(id).getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀립니다.");
        }

        LocalDateTime now = LocalDateTime.now();

        scheduleRepository.updateSchedule(id, contents, now);
        Schedule schedule = scheduleRepository.findScheduleById(id);

        scheduleRepository.updateAuthorName(schedule.getAuthorId(), userName, now);
        Author author = scheduleRepository.findAuthorById(schedule.getAuthorId());

        return new ScheduleResponseDto(schedule, author);

    }


    @Override
    public void deleteSchedule(Long id) {

        int deleteRow = scheduleRepository.deleteSchedule(id);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id를 찾을 수 없습니다.");
        }
    }


}

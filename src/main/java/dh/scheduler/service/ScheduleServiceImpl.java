package dh.scheduler.service;

import dh.scheduler.dto.ScheduleRequestDto;
import dh.scheduler.dto.ScheduleResponseDto;
import dh.scheduler.entity.Author;
import dh.scheduler.entity.Schedule;
import dh.scheduler.repository.ScheduleRepository;
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

        // requestDto 객체에서 authorId로 작성자 데이터를 찾아 반환
        Author author = scheduleRepository.findAuthorById(requestDto.getAuthorId());

        // requestDto 객체로 schedule 객체 생성
        Schedule schedule = new Schedule(
                author.getId(),
                requestDto.getTitle(),
                requestDto.getPassword(),
                requestDto.getContents()
        );

        // 생성한 schedule 객체를 DB에 저장
        schedule = scheduleRepository.saveSchedule(schedule);

        // ResponseDto 객체를 생성하여 return
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getAuthorId(),
                author.getUserName(),
                schedule.getTitle(),
                schedule.getPassword(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }


    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        // 게시글 id 값으로 일정 데이터를 찾아옴
        Schedule schedule = scheduleRepository.findScheduleById(id);

        // 찾아온 게시글 데이터의 author_id 값을 이용에 작성자 데이터를 찾아옴
        Author author = scheduleRepository.findAuthorById(schedule.getAuthorId());

        return new ScheduleResponseDto(schedule, author);
    }

    @Override
    public List<ScheduleResponseDto> findListSchedules(String date, Long id) {

        List<ScheduleResponseDto> findList = new ArrayList<>();

        // 작성자 id, 날짜 둘 다 없을 때 전체 목록 조회
        if(id == null || date == null) {
            findList = scheduleRepository.findAllScheduleLists();
        }

        // 작성자 id, 날짜 둘 다 값이 있을 때 조건에 맞는 목록 조회
        if(id != null || date != null) {
            findList = scheduleRepository.findScheduleListByNameWithDate(date, id);
        }

        // 작성자 id로 필터링한 목록 조회
        if (id != null) {
            findList = scheduleRepository.findScheduleListByName(id);
        }

        // 날짜로 필터링한 목록 조회
        if (date != null) {
            findList = scheduleRepository.findScheduleListByDate(date);
        }

        return findList;
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String userName, String contents, String password) {

        // 비밀번호가 틀릴 경우 400 상태코드
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

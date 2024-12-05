package dh.scheduler.controller;


import dh.scheduler.dto.ScheduleRequestDto;
import dh.scheduler.dto.ScheduleResponseDto;
import dh.scheduler.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }


    @GetMapping("/lists")
    public ResponseEntity<List<ScheduleResponseDto>> findListSchedules(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Long id,
            @PageableDefault(size = 10)Pageable pageable
            ) {
        return new ResponseEntity<>(scheduleService.findListSchedules(date, id, pageable), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto
            ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, requestDto.getUserName(),requestDto.getContents(), requestDto.getPassword()), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

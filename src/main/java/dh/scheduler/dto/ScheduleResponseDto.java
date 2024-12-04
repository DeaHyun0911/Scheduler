package dh.scheduler.dto;

import dh.scheduler.entity.Author;
import dh.scheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private Long author_id;
    private String userName;
    private String title;
    private String password;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ScheduleResponseDto(Schedule schedule, Author author) {
        this.id = schedule.getId();
        this.author_id = author.getId();
        this.userName = author.getUserName();
        this.title = schedule.getTitle();
        this.password = schedule.getPassword();
        this.contents = schedule.getContents();
        this.createDate = schedule.getCreatedAt();
        this.updateDate = schedule.getUpdatedAt();
    }
}

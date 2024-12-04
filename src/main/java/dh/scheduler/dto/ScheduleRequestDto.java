package dh.scheduler.dto;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private Long authorId;

    private String userName;

    private String email;

    private String title;

    private String password;

    private String contents;

}

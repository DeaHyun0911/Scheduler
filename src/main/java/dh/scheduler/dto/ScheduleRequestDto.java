package dh.scheduler.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private Long authorId;

    private String userName;

    private String email;

    @NotBlank(message = "제목은 필수값입니다.")
    private String title;

    @NotBlank(message = "비밀번호은 필수값입니다.")
    private String password;

    @NotBlank(message = "내용은 필수값입니다.")
    private String contents;

}

package dh.scheduler.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private Long authorId;

    private String userName;

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일형식이 아닙니다.")
    private String email;

    @NotBlank(message = "제목은 필수값입니다.")
    private String title;

    @NotBlank(message = "비밀번호은 필수값입니다.")
    @Size(min = 8, message = "비밀번호는 8자리 이상 입력해주세요.")
    private String password;

    @NotBlank(message = "내용은 필수값입니다.")
    @Size(max = 200, message = "최대 200글자까지 가능합니다.")
    private String contents;

}

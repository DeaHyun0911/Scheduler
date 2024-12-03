package dh.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String userName;
    private String title;
    private String password;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Schedule(String userName, String title, String password, String contents) {
        this.userName = userName;
        this.title = title;
        this.password = password;
        this.contents = contents;
        this.createDate = setCreateDate();
        this.updateDate = createDate;
    }

    public LocalDateTime setCreateDate() {
        LocalDateTime now = LocalDateTime.now();
        return this.createDate = now;
    }
}

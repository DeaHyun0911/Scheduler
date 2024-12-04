package dh.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private Long authorId;
    private String title;
    private String password;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(Long authorId, String title, String password, String contents) {
        this.authorId = authorId;
        this.title = title;
        this.password = password;
        this.contents = contents;
        this.createdAt = setCreatedAt();
        this.updatedAt = createdAt;
    }

    public LocalDateTime setCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        return this.createdAt = now;
    }

}

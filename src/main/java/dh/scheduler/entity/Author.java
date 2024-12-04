package dh.scheduler.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Author {
    private Long id;
    private String userName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Author(String userName) {
        this.userName = userName;
        this.createdAt = setCreateDate();
        this.updatedAt = createdAt;
    }

    public Author(Long id, String userName, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.userName = userName;
        this.createdAt = createAt;
        this.updatedAt = updateAt;
    }

    public LocalDateTime setCreateDate() {
        LocalDateTime now = LocalDateTime.now();
        return this.createdAt = now;
    }


}



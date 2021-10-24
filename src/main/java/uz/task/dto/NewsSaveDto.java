package uz.task.dto;

import java.time.ZonedDateTime;

public class NewsSaveDto {
    private Long id;
    private String description;
    private ZonedDateTime accept_date;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getAccept_date() {
        return accept_date;
    }

    public void setAccept_date(ZonedDateTime accept_date) {
        this.accept_date = accept_date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

package dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class PresenceDTO {
    private UserDTO user;
    private LocalDateTime timeMark;
}

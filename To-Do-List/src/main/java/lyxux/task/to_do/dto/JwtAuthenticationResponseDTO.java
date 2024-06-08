package lyxux.task.to_do.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponseDTO {
    private String token;
    private long userId;
    private String email;       //Additional
}

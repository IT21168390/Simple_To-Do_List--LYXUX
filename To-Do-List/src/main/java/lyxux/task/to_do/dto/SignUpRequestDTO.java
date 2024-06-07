package lyxux.task.to_do.dto;

import lombok.Data;

@Data
public class SignUpRequestDTO {
    private String id;
    private String name;
    private String email;
    private String password;
}

package ntukhpi.ddy.buzz.service;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.ddy.buzz.enums.documentType.documentType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty(message = "Почта не повинена бути пустою")
    @Email
    private String email;
    @NotEmpty(message = "Пароль не повинен бути пустим")
    private String password;
    @NotEmpty(message = "Поле з телеофном не повинно бути пустим")
    private String phone;
    @NotEmpty(message = "Поле з телеофном не повинно бути пустим")
    private documentType documentType;
}
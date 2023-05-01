package rea.yooolio.attestationspringapp.dto;

import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import rea.yooolio.attestationspringapp.entities.UserEntity;

public class UserDTO {
    @Size(min = 4, max = 256, message = "Имя пользователя должно содержать не менее 4-х символов.")
    private String username;
    @Size(min = 7, max = 32, message = "Пароль должен состоять как минимум из 7-ми символов.")
    private String password;
    private String confirmedPassword;

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull UserEntity convertToUserEntity(@NotNull UserDTO userDTO) {
        return new UserEntity(userDTO.username, userDTO.password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }
}

package com.cv_generator.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDTO{

        @Email(message = "El formato del email es invalido")
        @NotBlank(message = "El campo es obligatorio")
        String email;

        @Size(min = 8, message = "El campo debe tener minimo 8 caracteres")
        @NotBlank(message = "El campo es obligatorio")
        String password;
}

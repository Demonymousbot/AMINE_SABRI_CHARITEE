package com.example.userms.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDTO(
        @NotBlank(message = "Le nom ne doit pas être vide") String name,
        @Email(message = "L'email fourni n'est pas valide") String email,
        @Pattern(regexp = "\\+\\d{11,13}", message = "Le numéro de téléphone n'est pas valide") String phoneNumber,
        String address
) {
}

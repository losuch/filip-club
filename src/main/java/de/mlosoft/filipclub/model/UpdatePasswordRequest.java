package de.mlosoft.filipclub.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank
    private String newPassword;

    @NotBlank
    private String oldPassword;
}

package com.mitocode.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PatientDTO {

    @EqualsAndHashCode.Include
    private Integer idPatient;

    @NotNull // No nulo
    @NotEmpty // No vacío
    @Size(min = 3, max = 70, message = "{firstname.size}")
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 70, message = "{lastname.size}")
    private String lastName;

    @Size(min = 8, max = 8)
    private String dni;

    private String address;

    @Size(min = 9, max = 9)
    @Pattern(regexp = "[0-9]+") // Siempre para expresiones regulares.
    private String phone;

    @Email
    private String email;

    /*@Min(value = 1) Campos Min y Max solo se pueden poner en valores numéricos.
    @Max(value = 99) Un size no se puede poner en valor numérico porque el Size evalula la cantidad de caracteres y no el valor
    private int age;*/
}

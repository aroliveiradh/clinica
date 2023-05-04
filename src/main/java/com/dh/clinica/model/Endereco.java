package com.dh.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String rua;
    @NotNull
    @NotEmpty
    @NotBlank
    private String numero;
    @NotNull
    @NotEmpty
    @NotBlank
    private String cidade;
    @NotNull
    @NotEmpty
    @NotBlank
    private String estado;

}

package com.dh.clinica.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Dentista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String sobrenome;

    private String matricula;

    @Override
    public String toString() {
        return "Dentista{" +
                "nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", matricula='" + matricula + '\'' +
                '}';
    }
}

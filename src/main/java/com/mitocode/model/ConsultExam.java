package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@IdClass(ConsultExamPK.class)
public class ConsultExam {
    //Llaves primaria compuesta, porque tambien son llaves foraneas a otras tablas.
    @Id
    private Consult consult;

    @Id
    private Exam exam;

}

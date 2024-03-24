package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Consult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idConsult;

    //@ManyToOne, @OneToMany, @OneToMany, @OneToOne
    @ManyToOne // FK || Muchas consultas hacia un paciente. Siempre que hay una llave foránea es Many to One
    @JoinColumn(name = "id_patient", nullable = false, foreignKey = @ForeignKey(name = "FK_CONSULT_PATIENT"))
    private Patient patient; // Dentro de JPA usamos JPQL= facilidad de construir Queries.

    @ManyToOne
    @JoinColumn(name = "id_medic", nullable = false, foreignKey = @ForeignKey(name = "FK_CONSULT_MEDIC"))
    private Medic medic;

    @ManyToOne
    @JoinColumn(name = "id_specialty", nullable = false, foreignKey = @ForeignKey(name = "FK_CONSULT_SPECIALTY"))
    private Specialty specialty;

    @Column(nullable = false, length = 3)
    private String numConsult;

    @Column(nullable = false)
    private LocalDateTime consultDate;

    @OneToMany(mappedBy = "consult", cascade = CascadeType.ALL, orphanRemoval = true) // fetch = FetchType.EAGER) // Una consulta tiene mucho detalles - One Consult Have To Many ConsultDetail
    //mappedBy = "id_consult", || Como estoy relacionando con consultDetail, es para indicarle con que se esta relacionando
    //cascade = CascadeType.ALL)
    //orphanRemoval = true
    //fetch = FetchType.LAZY || Responsabilidad futura de traer mas información respecto a esta relación.
    //fetch = FetchType.EAGER || En Eager traer en el querytodo lo que esta relacionado.
    private List<ConsultDetail> details;
    //@Column(columnDefinition = "decimal(6,2)")
    //private double total;

}

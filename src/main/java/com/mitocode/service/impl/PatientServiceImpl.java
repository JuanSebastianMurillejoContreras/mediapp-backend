package com.mitocode.service.impl;

import com.mitocode.model.Patient;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IPatientRepo;
import com.mitocode.service.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//Stereotype | Estereotipo = sirve para indicarle programador para que sirve esa clase.
// Puedo colocar cualquier estereotipo y el proyecto funciona.
// Sin embargo, colocar los correctos ayuda al rpogramador a identificar que tipo de clase tengo

//@Controller // Se solía utilizar si el proyecte era con el clasico Time my life o JSP
//@Component // Para cuando Spring me gestione la clase pero no se si es un service o repository, entonces lo categorizo como component.
@Service // Acceso código de lógica de negocio.
@RequiredArgsConstructor
public class PatientServiceImpl extends CRUDImpl<Patient, Integer> implements IPatientService {

    //@Autowired
    private final IPatientRepo repo;

    @Override
    protected IGenericRepo<Patient, Integer> getRepo() {
        return repo;
    }
}

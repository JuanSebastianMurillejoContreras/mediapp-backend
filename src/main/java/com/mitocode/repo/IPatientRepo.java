package com.mitocode.repo;

import com.mitocode.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository - No es necesaro porque esta heredando de JPARepository (una interfaz propia de Spring)
// Spring asume que tu intención es hacer una instancia de esta interfaz y traerla para inyección de depenencias.
public interface IPatientRepo extends IGenericRepo<Patient, Integer>{

}

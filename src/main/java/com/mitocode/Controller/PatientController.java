package com.mitocode.Controller;

import com.mitocode.dto.PatientDTO;
import com.mitocode.dto.PatientRecord;
import com.mitocode.model.Patient;
import com.mitocode.service.IPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController // No es un estereotipo
@RequestMapping("/patients") //end points | Sustantivos en plural
@RequiredArgsConstructor
//Para dejar que se pueda consuir el servicio REST desde el Front
//@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    //@Autowired // Cuando se trabaja con spring MVC esta construido por Servlets.
    // Y pueden generar procesos bloqueantes.Y para eso se usan anotaciones como @Async
    // o nos olvidamos de MVC y empleamos WebFlux

    private final IPatientService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    /*public PatientController(IPatientService service) {
        this.service = service;
    }*/

    /*@GetMapping() -- || Manual form to do a DTO || ---
    public ResponseEntity<List<PatientRecord>> findAll(){
        //List<PatientDTO> list = service.findAll().stream().map(e -> {
        List<PatientRecord> list = service.findAll().stream().map(e -> new PatientRecord(
                    e.getIdPatient(),
                    e.getFirstName(),
                    e.getLastName(),
                    e.getDni(),
                    e.getAddress(),
                    e.getPhone(),
                    e.getEmail())

            /*PatientDTO dto = new PatientDTO();
            dto.setIdPatient(e.getIdPatient());
            dto.setPrimaryName(e.getFirstName());
            dto.setSurName(e.getLastName());
            dto.setDni(e.getDni());
            dto.setEmail(e.getEmail());
            dto.setAddress(e.getAddress());
            return dto;
        ).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }*/


    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll(){
        //List<PatientDTO> lst = service.findAll().stream().map(e -> mapper.map(e, PatientDTO.class)).toList();
        List<PatientDTO> lst = service.findAll().stream().map(this::converToDTO).toList();
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable("id") Integer id){
        Patient obj = service.findById(id);
        //return new ResponseEntity<>(mapper.map(obj, PatientDTO.class), HttpStatus.OK);
        return new ResponseEntity<>(converToDTO(obj), HttpStatus.OK);
    }

    /*@PostMapping
    public ResponseEntity<Patient> save(@RequestBody Patient patient){
        Patient obj = service.save(patient);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }*/

    @PostMapping
    public ResponseEntity<PatientDTO> save(@Valid @RequestBody PatientDTO dto){
        Patient obj = service.save(convertoToEntity(dto));
        //loalhost:8080/patient/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPatient()).toUri();
        //return new ResponseEntity<>(obj, HttpStatus.CREATED);
        //return ResponseEntity.created(location).body(obj); Generalmente no se hace esto, porque luego se repcupera con el location
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}") //modificar absolutamente todos los campos y el //@PatchMapping para modificar solo algunos de ellos.
    public ResponseEntity<PatientDTO> update(@Valid @PathVariable ("id") Integer id, @RequestBody PatientDTO dto){
        Patient obj = service.update(convertoToEntity(dto), id);
        return new ResponseEntity<>(converToDTO(obj), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id){
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //////////////////////////////HATEOAS/////////////////////////////////////
    // Solo se aplica a servicios que retornan información. Es decir a Gets

    @GetMapping("/hateoas/{id}")
    public EntityModel<PatientDTO> findByIdHateoas(@PathVariable("id")Integer id){
        EntityModel<PatientDTO> resource = EntityModel.of(converToDTO(service.findById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("patient-info1"));
        return resource;
    }

    //////////////////////////////////DTO/////////////////////////////////////
    private PatientDTO converToDTO(Patient obj){
        return mapper.map(obj, PatientDTO.class);
    }

    private Patient convertoToEntity(PatientDTO dto){
        return mapper.map(dto, Patient.class);
    }
}

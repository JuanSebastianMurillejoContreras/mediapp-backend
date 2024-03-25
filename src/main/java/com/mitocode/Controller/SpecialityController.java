package com.mitocode.Controller;

import com.mitocode.dto.SpecialtyDTO;
import com.mitocode.model.Specialty;
import com.mitocode.service.ISpecialtyService;
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

@RestController
@RequestMapping("/specialties")
@RequiredArgsConstructor
public class SpecialityController {

    private final ISpecialtyService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> findAll(){
        List<SpecialtyDTO> lst = service.findAll().stream().map(this::converToDTO).toList();
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable("id") Integer id){
        Specialty obj = service.findById(id);
        return new ResponseEntity<>(converToDTO(obj), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SpecialtyDTO> save(@Valid @RequestBody SpecialtyDTO dto){
        Specialty obj = service.save(convertoToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdSpecialty()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> update(@Valid @PathVariable ("id") Integer id, @RequestBody SpecialtyDTO dto){
        dto.setIdSpecialty(id);
        Specialty obj = service.update(convertoToEntity(dto), id);
        return new ResponseEntity<>(converToDTO(obj), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id){
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //////////////////////////////HATEOAS/////////////////////////////////////
    @GetMapping("/hateoas/{id}")
    public EntityModel<SpecialtyDTO> findByIdHateoas(@PathVariable("id")Integer id){
        EntityModel<SpecialtyDTO> resource = EntityModel.of(converToDTO(service.findById(id)));
        // localhost:8080/specialitys/1
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("specialty-info1"));
        return resource;
    }

    //////////////////////////////////DTO/////////////////////////////////////
    private SpecialtyDTO converToDTO(Specialty obj){
        return mapper.map(obj, SpecialtyDTO.class);
    }

    private Specialty convertoToEntity(SpecialtyDTO dto){
        return mapper.map(dto, Specialty.class);
    }
}

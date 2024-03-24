package com.mitocode.Controller;
import com.mitocode.dto.MedicDTO;
import com.mitocode.model.Medic;
import com.mitocode.service.IMedicService;
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
@RequestMapping("/medics")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class MedicController {

    private final IMedicService service;
    @Qualifier("medicMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<MedicDTO>> findAll(){
        List<MedicDTO> lst = service.findAll().stream().map(e -> converToDTO(e)).toList();
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicDTO> findById(@PathVariable("id") Integer id){
        Medic obj = service.findById(id);
        return new ResponseEntity<>(converToDTO(obj), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicDTO> save(@Valid @RequestBody MedicDTO dto){
        Medic obj = service.save(convertoToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedic()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicDTO> update(@Valid @PathVariable ("id") Integer id, @RequestBody MedicDTO dto){
        Medic obj = service.update(convertoToEntity(dto), id);
        return new ResponseEntity<>(converToDTO(obj), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id){
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //////////////////////////////HATEOAS/////////////////////////////////////
    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicDTO> findByIdHateoas(@PathVariable("id")Integer id){
        EntityModel<MedicDTO> resource = EntityModel.of(converToDTO(service.findById(id)));
        // localhost:8080/medics/1
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("medic-info1"));
        return resource;
    }

    //////////////////////////////////DTO/////////////////////////////////////
    private MedicDTO converToDTO(Medic obj){
        return mapper.map(obj, MedicDTO.class);
    }

    private Medic convertoToEntity(MedicDTO dto){
        return mapper.map(dto, Medic.class);
    }
}

package com.example.cr_motos_backend.controller.app;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cr_motos_backend.model.app.Motorcicle;
import com.example.cr_motos_backend.services.app.MotorcicleServices;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.RequestDTO;
import com.example.cr_motos_backend.utils.response.ResponseDTO;

@RestController
@RequestMapping("/motorcicle")
@CrossOrigin("*")
public class MotorcicleController {

    @Autowired
    private MotorcicleServices services;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Motorcicle>> createMotorcicle(@RequestParam("idUser") UUID idUser,
            @RequestParam("idBrand") UUID idBrand,
            @RequestBody RequestDTO<Motorcicle> request) throws InformationNotFoundException {
        Motorcicle motorcicle = this.services.createMotorcicle(idUser, idBrand, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO<Motorcicle>("Criada com sucesso", motorcicle));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Motorcicle>> updateMotorcicle(@RequestParam("idMotorcicle") UUID idMotorcicle,
            @RequestBody RequestDTO<Motorcicle> request) throws InformationNotFoundException {
        Motorcicle motorcicleUpdated = this.services.updateMotorcicle(idMotorcicle, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<Motorcicle>("Alterado com sucesso", motorcicleUpdated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMotorcicle(@RequestParam("idMotorcicle") UUID idMotorcicle)
            throws InformationNotFoundException {
        this.services.deleteMotorcicle(idMotorcicle);
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseDTO<Motorcicle>> getById(@RequestParam("idMotorcicle") UUID idMotorcicle)
            throws InformationNotFoundException {
        Motorcicle motoFounded = this.services.getById(idMotorcicle);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Motorcicle>("Moto encontrada", motoFounded));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<Motorcicle>>> getAllMotorcicles() {
        List<Motorcicle> motorcicles = this.services.getAllMotorcicles();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<List<Motorcicle>>("Motos encontradas", motorcicles));
    }

    @GetMapping("/motorcicles-by-user")
    public ResponseEntity<ResponseDTO<List<Motorcicle>>> getMotorciclesByUser(@RequestParam("idUser") UUID idUser)
            throws InformationNotFoundException {
        List<Motorcicle> motorcicles = this.services.getMotorciclesByUser(idUser);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<List<Motorcicle>>("Motos cadastradas", motorcicles));
    }

}

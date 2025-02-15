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

import com.example.cr_motos_backend.model.app.Part;
import com.example.cr_motos_backend.services.app.PartServices;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.RequestDTO;
import com.example.cr_motos_backend.utils.response.ResponseDTO;

@RestController
@RequestMapping("/part")
@CrossOrigin("*")
public class PartController {

    @Autowired
    private PartServices services;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Part>> createPart(@RequestParam("idBrand") UUID idBrand,
            @RequestBody RequestDTO<Part> request) throws InformationNotFoundException {
        Part part = this.services.createPart(idBrand, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<Part>("Criado com sucesso", part));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Part>> updatePart(@RequestParam("idPart") UUID idPart,
            @RequestBody RequestDTO<Part> request) throws InformationNotFoundException {
        Part partUpdated = this.services.updatePart(idPart, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Part>("Alterado com  sucesso", partUpdated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePart(@RequestParam("idPart") UUID idPart) throws InformationNotFoundException {
        this.services.deletePart(idPart);
        return ResponseEntity.status(HttpStatus.OK).body("Excluido com sucesso");
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseDTO<Part>> getById(@RequestParam("idPart") UUID idPart)
            throws InformationNotFoundException {
        Part partFounded = this.services.getById(idPart);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Part>("Peça encontrada", partFounded));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<Part>>> getAllParts() {
        List<Part> parts = this.services.getAllParts();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<List<Part>>("Peças encontradas", parts));
    }

}

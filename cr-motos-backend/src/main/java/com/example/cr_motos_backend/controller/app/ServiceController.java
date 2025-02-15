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

import com.example.cr_motos_backend.model.app.Service;
import com.example.cr_motos_backend.services.app.ServiceServices;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.RequestDTO;
import com.example.cr_motos_backend.utils.response.ResponseDTO;

@RestController
@RequestMapping("/service")
@CrossOrigin("*")
public class ServiceController {

    @Autowired
    private ServiceServices services;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Service>> createService(@RequestParam("idBrand") UUID idBrand,
            @RequestBody RequestDTO<Service> request) throws InformationNotFoundException {
        Service service = this.services.createService(idBrand, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<Service>("Criado com sucesso", service));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Service>> updatePart(@RequestParam("idService") UUID idService,
            @RequestBody RequestDTO<Service> request) throws InformationNotFoundException {
        Service serviceUpdated = this.services.updateService(idService, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<Service>("Alterado com  sucesso", serviceUpdated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteService(@RequestParam("idService") UUID idService)
            throws InformationNotFoundException {
        this.services.deleteService(idService);
        return ResponseEntity.status(HttpStatus.OK).body("Excluido com sucesso");
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseDTO<Service>> getById(@RequestParam("idService") UUID idService)
            throws InformationNotFoundException {
        Service serviceFounded = this.services.getById(idService);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<Service>("Serviço encontrado", serviceFounded));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<Service>>> getAllServices() {
        List<Service> servicesFounded = this.services.getAllServices();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<List<Service>>("Serviços encontrados", servicesFounded));
    }

}

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

import com.example.cr_motos_backend.model.app.Brand;
import com.example.cr_motos_backend.services.app.BrandServices;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.RequestDTO;
import com.example.cr_motos_backend.utils.response.ResponseDTO;

@RestController
@RequestMapping("/brand")
@CrossOrigin("*")
public class BrandController {

    @Autowired
    private BrandServices services;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Brand>> createBrand(@RequestParam("idAdm") UUID idAdm,
            @RequestBody RequestDTO<Brand> request) throws InformationNotFoundException {
        Brand brand = this.services.createBrand(idAdm, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<Brand>("Criado com sucesso", brand));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Brand>> updateBrand(@RequestParam("idBrand") UUID idBrand,
            @RequestBody RequestDTO<Brand> request) throws InformationNotFoundException {
        Brand brandUpdated = this.services.updateBrand(idBrand, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Brand>("Alterado com sucesso", brandUpdated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBrand(@RequestParam("idBrand") UUID idBrand)
            throws InformationNotFoundException {
        this.services.deleteBrand(idBrand);
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseDTO<Brand>> getById(@RequestParam("idBrand") UUID idBrand)
            throws InformationNotFoundException {
        Brand brandFounded = this.services.getById(idBrand);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Brand>("Marca encontrada", brandFounded));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<Brand>>> getAllBrands() {
        List<Brand> brands = this.services.getAllBrands();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<List<Brand>>("Marcas encontradas", brands));
    }

}

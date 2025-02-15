package com.example.cr_motos_backend.services.app;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.cr_motos_backend.model.access.Administrator;
import com.example.cr_motos_backend.model.app.Brand;
import com.example.cr_motos_backend.repositories.app.BrandRepository;
import com.example.cr_motos_backend.services.access.AdministratorServices;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.RequestDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrandServices {

    private BrandRepository repository;
    private AdministratorServices admServices;

    public Brand createBrand(UUID idAdm, RequestDTO<Brand> request) throws InformationNotFoundException {
        Administrator admFounded = this.admServices.getById(idAdm);
        Brand brand = new Brand(request);
        admFounded.addBrand(brand);

        return this.repository.save(brand);
    }

    public Brand updateBrand(UUID idBrand, RequestDTO<Brand> request) throws InformationNotFoundException {
        Brand brandFounded = getById(idBrand);

        brandFounded.setNameBrand(request.data().getNameBrand());

        return this.repository.save(brandFounded);
    }

    public void deleteBrand(UUID idBrand) throws InformationNotFoundException {
        Brand brandFounded = getById(idBrand);
        this.repository.delete(brandFounded);
    }

    public List<Brand> getAllBrands() {
        return this.repository.findAll();
    }

    public Brand getById(UUID idBrand) throws InformationNotFoundException {
        return this.repository.findById(idBrand)
                .orElseThrow(() -> new InformationNotFoundException("Marca não encontrada para o ID: " + idBrand));
    }

}

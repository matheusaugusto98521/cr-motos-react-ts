package com.example.cr_motos_backend.services.app;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.cr_motos_backend.model.app.Brand;
import com.example.cr_motos_backend.model.app.Part;
import com.example.cr_motos_backend.repositories.app.PartRepository;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.RequestDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PartServices {

    private PartRepository repository;
    private BrandServices brandServices;

    public Part createPart(UUID idBrand, RequestDTO<Part> request) throws InformationNotFoundException {
        Brand brandFounded = this.brandServices.getById(idBrand);

        Part part = new Part(request);
        brandFounded.addPart(part);

        return this.repository.save(part);
    }

    public Part updatePart(UUID idPart, RequestDTO<Part> request) throws InformationNotFoundException {
        Part partFounded = getById(idPart);

        var dataRequest = request.data();

        partFounded.setNamePart(dataRequest.getNamePart());
        partFounded.setDescriptionPart(dataRequest.getDescriptionPart());
        partFounded.setIsAvailable(dataRequest.getIsAvailable());
        partFounded.setPricePart(dataRequest.getPricePart());

        return this.repository.save(partFounded);
    }

    public void deletePart(UUID idPart) throws InformationNotFoundException {
        Part partFounded = getById(idPart);
        this.repository.delete(partFounded);
    }

    public Part getById(UUID idPart) throws InformationNotFoundException {
        return this.repository.findById(idPart)
                .orElseThrow(() -> new InformationNotFoundException("Peça não encontrada para o ID: " + idPart));
    }

    public List<Part> getAllParts() {
        return this.repository.findAll();
    }
}

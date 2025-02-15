package com.example.cr_motos_backend.services.app;

import java.util.List;
import java.util.UUID;

import com.example.cr_motos_backend.model.app.Brand;
import com.example.cr_motos_backend.model.app.Service;
import com.example.cr_motos_backend.repositories.app.ServiceRepository;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.RequestDTO;

import lombok.AllArgsConstructor;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceServices {

    private ServiceRepository repository;
    private BrandServices brandServices;

    public Service createService(UUID idBrand, RequestDTO<Service> request)
            throws InformationNotFoundException {
        Brand brandFounded = this.brandServices.getById(idBrand);

        Service service = new Service(request);

        service.setBrand(brandFounded);

        return this.repository.save(service);
    }

    public Service updateService(UUID idService, RequestDTO<Service> request) throws InformationNotFoundException {
        Service serviceFounded = getById(idService);

        var dataRequest = request.data();

        serviceFounded.setNameService(dataRequest.getNameService());
        serviceFounded.setDescriptionService(dataRequest.getDescriptionService());

        return this.repository.save(serviceFounded);
    }

    public void deleteService(UUID idService) throws InformationNotFoundException {
        Service serviceFounded = getById(idService);
        this.repository.delete(serviceFounded);
    }

    public List<Service> getAllServices() {
        return this.repository.findAll();
    }

    public Service getById(UUID idService) throws InformationNotFoundException {
        return this.repository.findById(idService)
                .orElseThrow(() -> new InformationNotFoundException("Serviço não encontrado para o ID: " + idService));
    }

}

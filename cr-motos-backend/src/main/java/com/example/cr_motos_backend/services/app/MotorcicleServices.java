package com.example.cr_motos_backend.services.app;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.cr_motos_backend.model.access.User;
import com.example.cr_motos_backend.model.app.Brand;
import com.example.cr_motos_backend.model.app.Motorcicle;
import com.example.cr_motos_backend.repositories.app.MotorcicleRepository;
import com.example.cr_motos_backend.services.access.UserServices;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.RequestDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MotorcicleServices {

    private MotorcicleRepository repository;
    private UserServices userServices;
    private BrandServices brandServices;

    public Motorcicle createMotorcicle(UUID idUser, UUID idBrand, RequestDTO<Motorcicle> request)
            throws InformationNotFoundException {
        User userFounded = this.userServices.getById(idUser);
        Brand brandFounded = this.brandServices.getById(idBrand);

        Motorcicle motorcicle = new Motorcicle(request);
        userFounded.addMotorcicle(motorcicle);
        brandFounded.addMotorcicle(motorcicle);

        return this.repository.save(motorcicle);
    }

    public Motorcicle updateMotorcicle(UUID idMotorcicle, RequestDTO<Motorcicle> request)
            throws InformationNotFoundException {
        Motorcicle motorcicleFounded = getById(idMotorcicle);

        var dataMoto = request.data();

        motorcicleFounded.setModel(dataMoto.getModel());
        motorcicleFounded.setPlate(dataMoto.getPlate());
        motorcicleFounded.setYear(dataMoto.getYear());
        motorcicleFounded.setColor(dataMoto.getColor());
        motorcicleFounded.setKmMotorcicle(dataMoto.getKmMotorcicle());

        return this.repository.save(motorcicleFounded);
    }

    public void deleteMotorcicle(UUID idMotorcicle) throws InformationNotFoundException {
        Motorcicle motorcicleFounded = getById(idMotorcicle);
        this.repository.delete(motorcicleFounded);
    }

    public List<Motorcicle> getAllMotorcicles() {
        return this.repository.findAll();
    }

    public Motorcicle getById(UUID idMotorcicle) throws InformationNotFoundException {
        return this.repository.findById(idMotorcicle)
                .orElseThrow(() -> new InformationNotFoundException("Moto não encontrada para o ID: " + idMotorcicle));
    }

    public List<Motorcicle> getMotorciclesByUser(UUID idUser) throws InformationNotFoundException {
        User user = this.userServices.getById(idUser);

        return user.getMotorcicles();
    }

}

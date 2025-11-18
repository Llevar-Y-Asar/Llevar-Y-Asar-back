package Llevar_Y_Asar.Llevar_Y_Asar_back.repositories;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Orden;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdenRepository extends MongoRepository<Orden, String> {
    List<Orden> findByUsuarioId(String usuarioId);
    List<Orden> findByEstado(String estado);
}

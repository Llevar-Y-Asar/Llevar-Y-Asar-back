package Llevar_Y_Asar.Llevar_Y_Asar_back.repositories;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Carrito;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CarritoRepository extends MongoRepository<Carrito, String> {
    Optional<Carrito> findByUsuarioId(String usuarioId);
}

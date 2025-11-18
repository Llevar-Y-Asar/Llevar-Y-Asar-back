package Llevar_Y_Asar.Llevar_Y_Asar_back.repositories;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByRut(String rut);
    Optional<Usuario> findByEmail(String email);
    boolean existsByRut(String rut);
    boolean existsByEmail(String email);
}

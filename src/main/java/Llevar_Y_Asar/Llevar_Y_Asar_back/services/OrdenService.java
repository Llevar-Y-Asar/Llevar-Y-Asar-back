package Llevar_Y_Asar.Llevar_Y_Asar_back.services;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Orden;
import Llevar_Y_Asar.Llevar_Y_Asar_back.repositories.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdenService {
    
    @Autowired
    private OrdenRepository ordenRepository;
    
    public List<Orden> obtenerTodas() {
        return ordenRepository.findAll();
    }
    
    public Optional<Orden> obtenerPorId(String id) {
        return ordenRepository.findById(id);
    }
    
    public List<Orden> obtenerPorUsuario(String usuarioId) {
        return ordenRepository.findByUsuarioId(usuarioId);
    }
    
    public List<Orden> obtenerPorEstado(String estado) {
        return ordenRepository.findByEstado(estado);
    }
    
    public Orden crear(Orden orden) {
        orden.setNumeroOrden("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return ordenRepository.save(orden);
    }
    
    public Orden actualizar(String id, Orden orden) {
        if (ordenRepository.existsById(id)) {
            orden.setId(id);
            return ordenRepository.save(orden);
        }
        return null;
    }
    
    public Orden cambiarEstado(String id, String nuevoEstado) {
        Optional<Orden> orden = ordenRepository.findById(id);
        if (orden.isPresent()) {
            orden.get().setEstado(nuevoEstado);
            return ordenRepository.save(orden.get());
        }
        return null;
    }
    
    public void cancelar(String id) {
        cambiarEstado(id, "CANCELADA");
    }
}

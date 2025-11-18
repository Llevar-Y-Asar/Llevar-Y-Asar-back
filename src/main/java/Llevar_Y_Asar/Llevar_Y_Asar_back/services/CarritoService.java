package Llevar_Y_Asar.Llevar_Y_Asar_back.services;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Carrito;
import Llevar_Y_Asar.Llevar_Y_Asar_back.models.CarritoItem;
import Llevar_Y_Asar.Llevar_Y_Asar_back.repositories.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;
    
    public Carrito obtenerPorUsuario(String usuarioId) {
        Optional<Carrito> carrito = carritoRepository.findByUsuarioId(usuarioId);
        return carrito.orElseGet(() -> crearCarritoNuevo(usuarioId));
    }
    
    private Carrito crearCarritoNuevo(String usuarioId) {
        Carrito carrito = new Carrito();
        carrito.setUsuarioId(usuarioId);
        carrito.setItems(new ArrayList<>());
        carrito.setTotal(0.0);
        return carritoRepository.save(carrito);
    }
    
    public Carrito agregarItem(String usuarioId, CarritoItem item) {
        Carrito carrito = obtenerPorUsuario(usuarioId);
        
        Optional<CarritoItem> itemExistente = carrito.getItems().stream()
                .filter(i -> i.getProductoId().equals(item.getProductoId()))
                .findFirst();
        
        if (itemExistente.isPresent()) {
            itemExistente.get().setCantidad(itemExistente.get().getCantidad() + item.getCantidad());
        } else {
            carrito.getItems().add(item);
        }
        
        recalcularTotal(carrito);
        return carritoRepository.save(carrito);
    }
    
    public Carrito eliminarItem(String usuarioId, String productoId) {
        Carrito carrito = obtenerPorUsuario(usuarioId);
        carrito.getItems().removeIf(item -> item.getProductoId().equals(productoId));
        recalcularTotal(carrito);
        return carritoRepository.save(carrito);
    }
    
    public Carrito vaciar(String usuarioId) {
        Carrito carrito = obtenerPorUsuario(usuarioId);
        carrito.setItems(new ArrayList<>());
        carrito.setTotal(0.0);
        return carritoRepository.save(carrito);
    }
    
    public void recalcularTotal(Carrito carrito) {
        Double total = carrito.getItems().stream()
                .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                .sum();
        carrito.setTotal(total);
        carrito.setFechaActualizacion(LocalDateTime.now());
    }
}

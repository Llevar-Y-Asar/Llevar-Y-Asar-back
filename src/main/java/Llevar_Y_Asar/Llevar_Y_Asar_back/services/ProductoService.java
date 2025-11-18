package Llevar_Y_Asar.Llevar_Y_Asar_back.services;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Producto;
import Llevar_Y_Asar.Llevar_Y_Asar_back.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }
    
    public List<Producto> obtenerActivos() {
        return productoRepository.findByActivoTrue();
    }
    
    public Optional<Producto> obtenerPorId(String id) {
        return productoRepository.findById(id);
    }
    
    public List<Producto> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }
    
    public List<Producto> buscar(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    public Producto crear(Producto producto) {
        return productoRepository.save(producto);
    }
    
    public Producto actualizar(String id, Producto producto) {
        if (productoRepository.existsById(id)) {
            producto.setId(id);
            return productoRepository.save(producto);
        }
        return null;
    }
    
    public void eliminar(String id) {
        productoRepository.deleteById(id);
    }
    
    public void desactivar(String id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            producto.get().setActivo(false);
            productoRepository.save(producto.get());
        }
    }
}

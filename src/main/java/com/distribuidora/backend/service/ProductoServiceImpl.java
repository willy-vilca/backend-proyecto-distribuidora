package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.ProductoDTO;
import com.distribuidora.backend.model.Producto;
import com.distribuidora.backend.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public ProductoDTO getProductoById(Integer id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + id));

        return ProductoDTO.builder()
                .id(p.getId())
                .cod(p.getCod())
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .precio(p.getPrecio())
                .stock(p.getStock())
                .subcategoriaId(p.getSubcategoria().getId())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> getProductosBySubcategoria(Integer subcategoriaId) {
        List<Producto> productos = productoRepository.findBySubcategoriaIdFetchJoin(subcategoriaId);

        return productos.stream().map(this::toDto).collect(Collectors.toList());
    }

    private ProductoDTO toDto(Producto p) {
        return ProductoDTO.builder()
                .id(p.getId())
                .cod(p.getCod())
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .precio(p.getPrecio())
                .stock(p.getStock())
                .subcategoriaId(p.getSubcategoria() != null ? p.getSubcategoria().getId() : null)
                .subcategoriaNombre(p.getSubcategoria() != null ? p.getSubcategoria().getNombre() : null)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> buscarProductosPorNombre(String busqueda) {
        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCase(busqueda);

        return productos.stream().map(this::toDtoSimple).collect(Collectors.toList());
    }

    private ProductoDTO toDtoSimple(Producto p) {
        return ProductoDTO.builder()
                .id(p.getId())
                .cod(p.getCod())
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .precio(p.getPrecio())
                .stock(p.getStock())
                .build();
    }
}


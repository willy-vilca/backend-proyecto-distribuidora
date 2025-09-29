package com.distribuidora.backend.controller;

import com.distribuidora.backend.dto.ProductoDTO;
import com.distribuidora.backend.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @GetMapping("/subcategoria/{id}")
    public ResponseEntity<List<ProductoDTO>> getProductosPorSubcategoria(@PathVariable("id") Integer subcategoriaId) {
        List<ProductoDTO> productos = productoService.getProductosBySubcategoria(subcategoriaId);
        return ResponseEntity.ok(productos);
    }
}

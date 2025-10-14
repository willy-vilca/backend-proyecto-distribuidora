package com.distribuidora.backend.controller;

import com.distribuidora.backend.dto.CategoriaConSubDTO;
import com.distribuidora.backend.model.Categoria;
import com.distribuidora.backend.model.Subcategoria;
import com.distribuidora.backend.service.CategoriaService;
import com.distribuidora.backend.service.SubcategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final SubcategoriaService subcategoriaService;

    // GET /api/categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> lista = categoriaService.getAllCategorias();
        return ResponseEntity.ok(lista);
    }

    // GET /api/categorias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoria(@PathVariable Integer id) {
        return categoriaService.getCategoriaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET /api/categorias/{id}/subcategorias
    @GetMapping("/{id}/subcategorias")
    public ResponseEntity<List<Subcategoria>> listarSubcategoriasPorCategoria(@PathVariable Integer id) {
        List<Subcategoria> subs = subcategoriaService.getSubcategoriasByCategoriaId(id);
        return ResponseEntity.ok(subs);
    }

    @GetMapping("/con-subcategorias")
    public ResponseEntity<List<CategoriaConSubDTO>> getCategoriasConSub() {
        List<CategoriaConSubDTO> lista = categoriaService.getCategoriasConSubcategorias();
        return ResponseEntity.ok(lista);
    }
}

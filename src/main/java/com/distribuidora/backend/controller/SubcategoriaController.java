package com.distribuidora.backend.controller;

import com.distribuidora.backend.dto.SubcategoriaDTO;
import com.distribuidora.backend.service.SubcategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subcategorias")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SubcategoriaController {

    private final SubcategoriaService subcategoriaService;

    // GET /api/subcategorias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SubcategoriaDTO> getSubcategoria(@PathVariable("id") Integer id) {
        SubcategoriaDTO dto = subcategoriaService.getSubcategoriaConCategoriaPadre(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}

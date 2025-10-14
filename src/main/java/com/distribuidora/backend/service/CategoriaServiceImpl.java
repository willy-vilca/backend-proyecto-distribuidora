package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.CategoriaConSubDTO;
import com.distribuidora.backend.dto.SubcategoriaDTO;
import com.distribuidora.backend.model.Categoria;
import com.distribuidora.backend.model.Subcategoria;
import com.distribuidora.backend.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> getCategoriaById(Integer id) {
        return categoriaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaConSubDTO> getCategoriasConSubcategorias() {
        List<Categoria> categorias = categoriaRepository.findAllWithSubcategorias();
        return categorias.stream().map(this::toDto).collect(Collectors.toList());
    }

    private CategoriaConSubDTO toDto(Categoria c) {
        List<SubcategoriaDTO> subs = c.getSubcategorias() == null ? List.of() :
                c.getSubcategorias().stream()
                        .map(s -> {
                            SubcategoriaDTO dto = new SubcategoriaDTO();
                            dto.setId(s.getId());
                            dto.setNombre(s.getNombre());
                            return dto;
                        })
                        .collect(Collectors.toList());

        CategoriaConSubDTO dto = new CategoriaConSubDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setSubcategorias(subs);
        return dto;
    }
}

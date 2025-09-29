package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.SubcategoriaDTO;
import com.distribuidora.backend.model.Subcategoria;
import com.distribuidora.backend.repository.SubcategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubcategoriaServiceImpl implements SubcategoriaService {

    private final SubcategoriaRepository subcategoriaRepository;

    @Override
    public List<Subcategoria> getSubcategoriasByCategoriaId(Integer categoriaId) {
        return subcategoriaRepository.findByCategoriaPadre_Id(categoriaId);
    }

    @Override
    @Transactional(readOnly = true)
    public SubcategoriaDTO getSubcategoriaConCategoriaPadre(Integer subcategoriaId) {
        Optional<Subcategoria> opt = subcategoriaRepository.findById(subcategoriaId);
        if (opt.isEmpty()) {
            return null;
        }

        Subcategoria s = opt.get();

        // Accedemos a s.getCategoriaPadre() dentro de la transacción
        Integer idCat = null;
        String nombreCat = null;
        if (s.getCategoriaPadre() != null) {
            idCat = s.getCategoriaPadre().getId();
            nombreCat = s.getCategoriaPadre().getNombre();
        }

        return SubcategoriaDTO.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .idCategoriaPadre(idCat)
                .nombreCategoriaPadre(nombreCat)
                .build();
    }
}
package com.distribuidora.backend.service;

import com.distribuidora.backend.dto.AuthResponse;
import com.distribuidora.backend.dto.LoginRequest;
import com.distribuidora.backend.dto.RegisterRequest;
import com.distribuidora.backend.dto.CambioPasswordDTO;
import com.distribuidora.backend.dto.ActualizarUsuarioDTO;
import com.distribuidora.backend.model.Usuario;
import com.distribuidora.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse register(RegisterRequest req) {
        if (usuarioRepository.existsByCorreo(req.getCorreo())) {
            return new AuthResponse(null, null, null,null,null, "El correo ya se encuentra registrado en otra cuenta, intente con otro o inicie sesión.", null);
        }

        Usuario u = Usuario.builder()
                .correo(req.getCorreo().toLowerCase())
                .nombre(req.getNombre())
                .telefono(req.getTelefono())
                .contrasena(passwordEncoder.encode(req.getContrasena()))
                .fechaRegistro(LocalDateTime.now())
                .dni(req.getDni())
                .build();

        usuarioRepository.save(u);
        return new AuthResponse(u.getId(), u.getCorreo(), u.getNombre(), u.getTelefono(),u.getDni(), "Usuario registrado correctamente", null);
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        return usuarioRepository.findByCorreo(req.getCorreo().toLowerCase())
                .map(u -> {
                    if (passwordEncoder.matches(req.getContrasena(), u.getContrasena())) {
                        return new AuthResponse(u.getId(), u.getCorreo(), u.getNombre(), u.getTelefono(),u.getDni(), "Login correcto", null);
                    } else {
                        return new AuthResponse(null, null, null,null,null, "Credenciales inválidas, la contraseña es incorrecta, intente nuevamente por favor.", null);
                    }
                }).orElseGet(() -> new AuthResponse(null, null, null, null,null, "Usuario no encontrado, el correo que ingreso no está asociada a ninguna cuenta registrada", null));
    }

    @Override
    public ResponseEntity<?> cambiarPassword(CambioPasswordDTO dto) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCorreo(dto.getEmail());

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"mensaje\": \"El correo ingresado es incorrecto.\"}");
        }

        Usuario usuario = optionalUsuario.get();

        // Verificar contraseña antigua
        if (!passwordEncoder.matches(dto.getOldPassword(), usuario.getContrasena())) {
            return ResponseEntity.badRequest().body("{\"mensaje\": \"La contraseña actual es incorrecta\"}");
        }

        // Actualizar contraseña nueva
        usuario.setContrasena(passwordEncoder.encode(dto.getNewPassword()));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("{\"mensaje\": \"Contraseña actualizada correctamente\"}");
    }

    @Override
    public ResponseEntity<?> actualizarDatosUsuario(ActualizarUsuarioDTO dto) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(dto.getIdUsuario());

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"mensaje\": \"Usuario no encontrado\"}");
        }

        Usuario usuario = usuarioOptional.get();

        // Verificar si el correo nuevo es diferente del actual
        if (!usuario.getCorreo().equalsIgnoreCase(dto.getCorreo())) {
            // Verificar si el nuevo correo ya está en uso
            Optional<Usuario> correoExistente = usuarioRepository.findByCorreoAndNotId(dto.getCorreo(), dto.getIdUsuario());
            if (correoExistente.isPresent()) {
                return ResponseEntity.badRequest().body("{\"mensaje\": \"El nuevo correo ya está siendo utilizado por otro usuario\"}");
            }
            usuario.setCorreo(dto.getCorreo());
        }

        usuario.setNombre(dto.getNombre());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDni(dto.getDni());

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("{\"mensaje\": \"Los datos de su cuenta han sido actualizados correctamente\"}");
    }
}
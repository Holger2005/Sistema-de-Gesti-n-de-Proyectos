package com.krakedev.proyectos.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public Usuario registrar(Usuario usuario) {

		String contrasenaEncriptada = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());

		Optional<Usuario> existente = usuarioRepository.findByUsername(usuario.getUsername());

		if (existente.isPresent()) {
			throw new RuntimeException("El username ya está registrado");
		}

		usuario.setPassword(contrasenaEncriptada);
		return usuarioRepository.save(usuario);
	}

	public Usuario autenticar(String username, String passwordIngresada) {
		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		if (!BCrypt.checkpw(passwordIngresada, usuario.getPassword())) {

			throw new RuntimeException("Contraseña incorrecta");
		}

		return usuario;
	}
}

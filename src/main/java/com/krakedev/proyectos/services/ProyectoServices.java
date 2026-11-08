package com.krakedev.proyectos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.repositories.ProyectoRepository;

@Service
public class ProyectoServices {

	private final ProyectoRepository proyectoRepository;

	public ProyectoServices(ProyectoRepository proyectoRepository) {
		this.proyectoRepository = proyectoRepository;
	}

	public Proyecto buscar(int id) {

		return proyectoRepository.findById(id).orElseThrow(() -> new RuntimeException("Proyecto no existe"));
	}

	public Proyecto guardar(Proyecto proyecto) {

		if (!proyectoRepository.existsById(proyecto.getId())) {
			return proyectoRepository.save(proyecto);
		}
		return null;
	}

	public Proyecto actualizar(int id, Proyecto datos) {

		Proyecto proyecto = buscar(id);

		if (proyecto != null) {

			proyecto.setDescripcion(datos.getDescripcion());
			proyecto.setNombre(datos.getNombre());
			return proyectoRepository.save(proyecto);
		}

		return null;
	}

	public boolean eliminar(int id) {

		if (proyectoRepository.existsById(id)) {
			proyectoRepository.deleteById(id);
			return true;
		}

		return false;
	}
	
	public List<Proyecto> listar() {

		return proyectoRepository.findAll();
	}

}

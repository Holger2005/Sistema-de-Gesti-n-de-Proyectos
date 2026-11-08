package com.krakedev.proyectos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.repositories.EmpleadoRepository;

@Service
public class EmpleadoServices {

	private final EmpleadoRepository empleadoRepository;

	public EmpleadoServices(EmpleadoRepository empleadoRepository) {
		this.empleadoRepository = empleadoRepository;
	}

	public Empleado buscar(int id) {

		return empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no existe"));

	}

	public Empleado guardar(Empleado empleado) {

		if (!empleadoRepository.existsById(empleado.getId())) {
			return empleadoRepository.save(empleado);
		}

		return null;
	}

	public Empleado actualizar(int id, Empleado datos) {

		Empleado empleado = buscar(id);

		if (empleado != null) {

			empleado.setCargo(datos.getCargo());
			empleado.setNombre(datos.getNombre());
			return empleadoRepository.save(empleado);
		}

		return null;
	}

	public boolean eliminar(int id) {

		if (empleadoRepository.existsById(id)) {
			empleadoRepository.deleteById(id);
			return true;
		}

		return false;
	}

	public List<Empleado> listar() {

		return empleadoRepository.findAll();
	}

}

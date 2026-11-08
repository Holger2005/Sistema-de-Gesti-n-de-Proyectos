package com.krakedev.proyectos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.repositories.EmpleadoRepository;
import com.krakedev.proyectos.repositories.ProyectoRepository;
import com.krakedev.proyectos.repositories.TareaRepository;

@Service
public class TareaServices {

	private final TareaRepository tareaRepository;
	private final EmpleadoRepository empleadoRepository;
	private final ProyectoRepository proyectoRepository;

	public TareaServices(TareaRepository tareaRepository, EmpleadoRepository empleadoRepository,
			ProyectoRepository proyectoRepository) {
		this.tareaRepository = tareaRepository;
		this.empleadoRepository = empleadoRepository;
		this.proyectoRepository = proyectoRepository;
	}

	public Tarea buscar(int id) {

		return tareaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarea no existe"));
	}

	public Tarea guardar(Tarea tarea) {

		Proyecto proyecto = proyectoRepository.findById(tarea.getProyecto().getId())
				.orElseThrow(() -> new RuntimeException("Proyecto no existe"));

		List<Empleado> empleadosDB = new ArrayList<>();

		for (Empleado empleado : tarea.getEmpleados()) {

			Empleado empleadoEncontrado = empleadoRepository.findById(empleado.getId())
					.orElseThrow(() -> new RuntimeException("Empleado no existe"));
			empleadosDB.add(empleadoEncontrado);
		}

		tarea.setProyecto(proyecto);
		tarea.setEmpleados(empleadosDB);
		return tareaRepository.save(tarea);
	}

	public Tarea actualizar(int id, Tarea datos) {

		Tarea tarea = buscar(id);

		if (tarea == null) {

			return null;
		}

		tarea.setCostoEstimado(datos.getCostoEstimado());
		tarea.setEmpleados(datos.getEmpleados());
		tarea.setDescripcion(datos.getDescripcion());
		tarea.setFechaLimite(datos.getFechaLimite());
		return tareaRepository.save(tarea);
	}

	public boolean eliminar(int id) {

		if (tareaRepository.existsById(id)) {
			tareaRepository.deleteById(id);
			return true;
		}

		return false;
	}

	public List<Tarea> listar() {

		return tareaRepository.findAll();
	}

}

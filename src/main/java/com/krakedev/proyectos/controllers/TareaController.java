package com.krakedev.proyectos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.services.TareaServices;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

	@Autowired
	private TareaServices tareaService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> crear(@RequestBody Tarea tarea) {
		try {
			Tarea creada = tareaService.guardar(tarea);
			return ResponseEntity.status(HttpStatus.CREATED).body(creada);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al crear la tarea: " + e.getMessage());
		}
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> listar() {
		try {
			List<Tarea> tareas = tareaService.listar();
			return ResponseEntity.ok(tareas);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al listar tareas: " + e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarTareaId(@PathVariable int id) {

		try {
			Tarea tarea = tareaService.buscar(id);
			if (tarea != null) {
				return ResponseEntity.ok(tarea);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrado");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al buscar la tarea: " + e.getMessage());

		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> elimminar(@PathVariable int id) {

		try {
			tareaService.eliminar(id);
			return ResponseEntity.ok("Tarea eliminado correctamente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al eliminar la tarea: " + e.getMessage());
		}
	}
}

package com.krakedev.proyectos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<?> listar() {
		try {
			List<Tarea> tareas = tareaService.listar();
			return ResponseEntity.ok(tareas);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al listar tareas: " + e.getMessage());
		}
	}
}
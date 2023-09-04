package com.edhm.mascota.api.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.edhm.mascota.api.entities.Mascota;
import com.edhm.mascota.api.services.MascotaService;

@RestController
public class MascotaController {
	private static final Logger logger = LoggerFactory.getLogger(MascotaController.class);
	@Value("${app.storage.path}")
	private String STORAGEPATH;
	@Autowired
	private MascotaService mascotaService;

	@GetMapping("/mascotas")
	public List<Mascota> mascotas() {
		logger.info("call mascotas");
		List<Mascota> mascotas = mascotaService.findAll();
		logger.info("mascotas: " + mascotas);
		return mascotas;
	}

	@GetMapping("/mascotas/fotos/{filename:.+}")
	public ResponseEntity<Resource> files(@PathVariable String filename) throws Exception {
		logger.info("call fotos: " + filename);
		Path path = Paths.get(STORAGEPATH).resolve(filename);
		logger.info("Path: " + path);
		if (!Files.exists(path)) {
			return ResponseEntity.notFound().build();
		}
		org.springframework.core.io.Resource resource = new UrlResource(path.toUri());
		logger.info("Resource: " + resource);
		return ResponseEntity.ok()
				.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
						"inline;filename=\"" + resource.getFilename() + "\"")
				.header(org.springframework.http.HttpHeaders.CONTENT_TYPE,
						Files.probeContentType(Paths.get(STORAGEPATH).resolve(filename)))
				.header(org.springframework.http.HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
				.body(resource);
	}

	@PostMapping("/productos")
	public Mascota crear(@RequestParam(name = "foto", required = false) MultipartFile foto,
			@RequestParam("nombre") String nombre, @RequestParam("especie") String especie,
			@RequestParam("edad") int edad, @RequestParam("color") String color) throws Exception {
		logger.info("call crear(" + nombre + ", " + especie + ", " + edad + ", " + color + ", " + foto + ")");
		Mascota mascota = new Mascota();
		mascota.setNombre(nombre);
		mascota.setEspecie(especie);
		mascota.setEdad(edad);
		mascota.setColor(color);

		if (foto != null && !foto.isEmpty()) {
			String filename = System.currentTimeMillis()
					+ foto.getOriginalFilename().substring(foto.getOriginalFilename().lastIndexOf("."));
			mascota.setFoto(filename);
			if (Files.notExists(Paths.get(STORAGEPATH))) {
				Files.createDirectories(Paths.get(STORAGEPATH));
			}
			Files.copy(foto.getInputStream(), Paths.get(STORAGEPATH).resolve(filename));
		}
		mascotaService.save(mascota);
		return mascota;
	}

	@DeleteMapping("/mascotas/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		logger.info("call eliminar: " + id);
		mascotaService.deleteById(id);
		return ResponseEntity.ok().body("Registro eliminado");
	}

	@GetMapping("/mascotas/{id}")
	public Mascota obtener(@PathVariable Long id) {
		logger.info("call obtener: " + id);
		Mascota mascota = mascotaService.findById(id);
		return mascota;
	}

}

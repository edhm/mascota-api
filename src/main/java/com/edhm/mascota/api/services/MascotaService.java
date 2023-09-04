package com.edhm.mascota.api.services;

import java.util.List;

import com.edhm.mascota.api.entities.Mascota;

public interface MascotaService {
	public List<Mascota> findAll();

	public Mascota findById(Long id);

	public void save(Mascota mascota);

	public void deleteById(Long id);

}

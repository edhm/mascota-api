package com.edhm.mascota.api.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.edhm.mascota.api.entities.Mascota;

public interface MascotaRepository extends CrudRepository<Mascota, Long> {
	@Override
	List<Mascota> findAll();
}

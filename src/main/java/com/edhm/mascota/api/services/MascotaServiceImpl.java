package com.edhm.mascota.api.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edhm.mascota.api.entities.Mascota;
import com.edhm.mascota.api.repositories.MascotaRepository;

@Service
@Transactional
public class MascotaServiceImpl implements MascotaService {
	@Autowired
	MascotaRepository mascotaRepository;

	@Override
	public List<Mascota> findAll() {
		return mascotaRepository.findAll();
	}

	@Override
	public Mascota findById(Long id) {
		return mascotaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe registro"));
	}

	@Override
	public void save(Mascota mascota) {
		mascotaRepository.save(mascota);

	}

	@Override
	public void deleteById(Long id) {
		mascotaRepository.deleteById(id);

	}

}

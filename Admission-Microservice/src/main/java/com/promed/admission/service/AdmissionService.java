package com.promed.admission.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promed.admission.exception.AdmissionNotFoundException;
import com.promed.admission.model.AdmissionModel;
import com.promed.admission.repo.AdmissionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
* Admission Service 
* 
* @author  Murali Parvathareddi
* @version 1.0
* @since   2023-04-29
*/
@Service
public class AdmissionService {

	@Autowired
	private AdmissionRepository admissionRepository;

	
	public Flux<AdmissionModel> getAllAdmissions() {
		return admissionRepository.findAll().switchIfEmpty(Flux.error(new AdmissionNotFoundException("Records Not Exist")));

	}

	public Mono<AdmissionModel> getAdmissionById(String id) {
		return admissionRepository.findById(id).switchIfEmpty(Mono.error(new AdmissionNotFoundException("No admission by ID: " + id)));
	}

	public Mono<AdmissionModel> createAdmission(final AdmissionModel admission) {
		return admissionRepository.insert(admission);

	}

	public Mono<AdmissionModel> updateAdmission(AdmissionModel admission,String id) {
		
		return admissionRepository.findById(id).switchIfEmpty(Mono.error(new AdmissionNotFoundException("admission not found for update, ID: " + id))).filter(Objects::nonNull)
				.map(u -> {	
					u.setBirthDate(admission.getBirthDate());
				    u.setCategory(admission.getCategory());
				    u.setGender(admission.getGender());
				    u.setName(admission.getName());
				    u.setSource(admission.getSource());
				return u;}).flatMap(admissionRepository::save);

	}

	public Mono<AdmissionModel> deleteAdmission(String id) {

		return admissionRepository.findById(id).switchIfEmpty(Mono.error(new AdmissionNotFoundException("admission not found for deletion, ID: " + id))).filter(Objects::nonNull)
				.flatMap(admissionToBeDeleted -> admissionRepository.delete(admissionToBeDeleted)
						.then(Mono.just(admissionToBeDeleted)));
	}

}

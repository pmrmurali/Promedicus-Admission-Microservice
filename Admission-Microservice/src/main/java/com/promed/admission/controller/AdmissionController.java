package com.promed.admission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.promed.admission.model.AdmissionModel;
import com.promed.admission.service.AdmissionService;
import com.promed.admission.util.Constants;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
* Admission Controller is doing REST API CURD operations
* GET,POST,PUT,DELETE
* @author  Murali Parvathareddi
* @version 1.0
* @since   2023-04-29
*/
@RestController
@RequestMapping(path="/api/admissions",produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AdmissionController {

	@Autowired
	AdmissionService admissionService;

	/**
	 * Get All operations
	 * @return Flux<AdmissionModel>
	 */
	@GetMapping
	@CrossOrigin(origins = Constants.ORIGIN_URL)
	@ResponseStatus(HttpStatus.OK)
	public Flux<AdmissionModel> retrieveAllAdmissions() {
		return admissionService.getAllAdmissions();

	}

	/**
	 * Param Id : Get By Admission Id  using GET operation
	 * 
	 * @return Mono<AdmissionModel>
	 */
	@GetMapping("/{id}")
	@CrossOrigin(origins = Constants.ORIGIN_URL)
	@ResponseStatus(HttpStatus.OK)
	public Mono<AdmissionModel> retrieveAdmission(@PathVariable String id) {
		return admissionService.getAdmissionById(id);

	}

	/**
	 * Admission creation by using Post Operation
	 * @Param AdmissionModel : Admission details
	 * @status 201 Created
	 * @return Mono<AdmissionModel>
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@CrossOrigin(origins = Constants.ORIGIN_URL)
	public Mono<AdmissionModel> createAdmission(@RequestBody @Valid AdmissionModel admissionModel) {
		return admissionService.createAdmission(admissionModel);
	}

	/**
	 * Admission Update by using PUT Operation
	 * @Param AdmissionModel : Admission details
	 * @status 200 
	 * @return Mono<AdmissionModel>
	 */
	@PutMapping(path="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@CrossOrigin(origins = Constants.ORIGIN_URL)
	public Mono<AdmissionModel> updateAdmission(@RequestBody AdmissionModel admissionModel, @PathVariable String id) {
		return admissionService.updateAdmission(admissionModel,id);
	}

	/**
	 * Delete Admission  by using DELETE Operation
	 * @Param AdmissionModel : Admission details
	 * @status 200 
	 * @return Mono<AdmissionModel>
	 */
	@DeleteMapping("/{id}")
	@CrossOrigin(origins = Constants.ORIGIN_URL)
	@ResponseStatus(HttpStatus.OK)
	public Mono<AdmissionModel> deleteAdmission(@Valid @PathVariable(name = "id", required = false)  String id) {
		return admissionService.deleteAdmission(id);
	}

}

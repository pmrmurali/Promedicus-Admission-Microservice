package com.promed.admission;

import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.promed.admission.controller.AdmissionController;
import com.promed.admission.model.AdmissionModel;
import com.promed.admission.repo.AdmissionRepository;
import com.promed.admission.service.AdmissionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = AdmissionController.class)
public class AdmissionControllerTest {

	@MockBean
	protected AdmissionService admissionService;

	@MockBean
	AdmissionRepository repository;

	@Autowired
	WebTestClient webTestClient;

	@Test
	public void testCreateAdmission() throws Exception {

		
		String uri = "/api/admissions";

		AdmissionModel admission = AdmissionModel.builder().name("Ramesh").birthDate(LocalDate.now()).gender("Male")
				.category("Normal").build();

		Mockito.when(admissionService.createAdmission(admission)).thenReturn(Mono.just(admission));

		Mockito.when(repository.insert(admission)).thenReturn(Mono.just(admission));

		webTestClient.post().uri(uri).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromObject(admission))
				.exchange().expectStatus().isCreated();

		Mockito.verify(admissionService, times(1)).createAdmission(admission);

	}

	@Test
	public void testGetAdmission() throws Exception {

		String uri = "/api/admissions";

		AdmissionModel admission = AdmissionModel.builder().name("Ramesh").birthDate(LocalDate.now()).gender("Male")
				.category("Normal").build();

		AdmissionModel admission1 = AdmissionModel.builder().name("Murali").birthDate(LocalDate.now()).gender("Female")
				.category("Inpatient").build();

		List<AdmissionModel> list = new ArrayList<AdmissionModel>();
		list.add(admission);
		list.add(admission1);

		Flux<AdmissionModel> admissionFlux = Flux.fromIterable(list);

		Mockito.when(admissionService.getAllAdmissions()).thenReturn(admissionFlux);

		webTestClient.get().uri(uri).header(HttpHeaders.ACCEPT, "application/json").exchange().expectStatus().isOk()
				.expectBodyList(AdmissionModel.class);

	}

}

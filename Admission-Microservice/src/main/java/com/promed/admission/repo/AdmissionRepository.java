package com.promed.admission.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.promed.admission.model.AdmissionModel;

@Repository
public interface AdmissionRepository extends ReactiveMongoRepository<AdmissionModel, String>{

}

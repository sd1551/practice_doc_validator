package org.practice_validator;

import org.practice_validator.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, String> {

    @Query("SELECT d FROM DocumentEntity d WHERE d.validationResult IS NULL OR d.validationResult = false")
    List<DocumentEntity> findUnvalidatedDocuments();
}
package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.ResponseDtoEntrevistaPendientes;
import com.zonatech.app.infrastructure.entities.EntrevistaEstudiantesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntrevistaEstudiantesEntityRepository extends JpaRepository<EntrevistaEstudiantesEntity, Long> {
    boolean existsByEntrevistaId(Long id);

    @Query(value = """
            SELECT 
                ee.id AS idEntrevistaEstudiante, 
                CONCAT(u.nombre, ' ', u.apellidos) AS nombreEstudiante, 
                ee.valoracion AS notaFinal
            FROM entrevista_estudiantes ee
            INNER JOIN usuarios u ON ee.id_estudiante = u.id
            WHERE ee.id_entrevista = :idEntrevista
              AND (:filter IS NULL 
                   OR LOWER(CONCAT(u.nombre, ' ', u.apellidos)) LIKE LOWER(CONCAT('%', :filter, '%')))
            """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM entrevista_estudiantes ee
                    INNER JOIN usuarios u ON ee.id_estudiante = u.id
                    WHERE ee.id_entrevista = :idEntrevista
                      AND (:filter IS NULL 
                           OR LOWER(CONCAT(u.nombre, ' ', u.apellidos)) LIKE LOWER(CONCAT('%', :filter, '%')))
                    """,
            nativeQuery = true)
    Page<ResponseDtoEntrevistaPendientes> findEntrevistaPendientes(
            @Param("idEntrevista") Long idEntrevista,
            @Param("filter") String filter,
            Pageable pageable);

    EntrevistaEstudiantesEntity findByEntrevistaIdAndEstudianteId(Long entrevistaId, Long estudianteId);
}
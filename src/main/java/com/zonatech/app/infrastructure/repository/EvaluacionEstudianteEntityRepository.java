package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.infrastructure.entities.EvaluacionEstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionEstudianteEntityRepository extends JpaRepository<EvaluacionEstudianteEntity, Long> {
    boolean existsByEvaluacionIdAndEstudianteId(Long evaluacionId, Long estudianteId);

    boolean existsByEvaluacionId(Long evaluacionId);

    @Query(value = """
                SELECT 
                    ee.id AS idEvaluacionEstudiante, 
                    CONCAT(u.nombre, ' ', u.apellidos) AS estudiante, 
                    ee.calificacion_final 
                FROM evaluacion_estudiante ee
                INNER JOIN usuarios u ON ee.id_estudiante = u.id
                WHERE ee.id_evaluacion = :idEvaluacion
            """, nativeQuery = true)
    List<Object[]> findEvaluacionesByIdEvaluacion(@Param("idEvaluacion") Long idEvaluacion);

}
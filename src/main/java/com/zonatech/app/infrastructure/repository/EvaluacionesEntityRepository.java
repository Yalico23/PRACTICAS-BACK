package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.EvaluacionResumen;
import com.zonatech.app.infrastructure.entities.EvaluacionesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionesEntityRepository extends JpaRepository<EvaluacionesEntity, Long> {

    List<EvaluacionesEntity> findByMentorId(Long idMentor);

    @Query(value = """
                SELECT 
                    e.id,
                    e.titulo,
                    e.descripcion,
                    CONCAT(mentor.nombre, ' ', mentor.apellidos) AS mentor,
                    e.tags AS tecnologia,
                    CASE 
                        WHEN ee.completado = TRUE THEN 'Completado'
                        WHEN ee.id IS NOT NULL AND ee.completado = FALSE THEN 'En Progreso'
                        ELSE 'Disponible'
                    END AS estado,
                    CONCAT(
                        FLOOR(SUM(p.tiempo) / 60), 'h ',
                        SUM(p.tiempo) % 60, ' min'
                    ) AS tiempo,
                     ee.feedback AS feedback
                FROM evaluaciones e
                JOIN usuarios mentor ON e.id_mentor = mentor.id
                JOIN users_roles ur_mentor ON mentor.id = ur_mentor.user_id
                JOIN roles r_mentor ON ur_mentor.role_id = r_mentor.id
                LEFT JOIN evaluacion_estudiante ee ON e.id = ee.id_evaluacion 
                    AND ee.id_estudiante = (
                        SELECT u_estudiante.id 
                        FROM usuarios u_estudiante
                        JOIN users_roles ur_estudiante ON u_estudiante.id = ur_estudiante.user_id
                        JOIN roles r_estudiante ON ur_estudiante.role_id = r_estudiante.id
                        WHERE u_estudiante.email = :emailEstudiante
                          AND r_estudiante.nombre = 'ROLE_ESTUDIANTE'
                        LIMIT 1
                    )
                LEFT JOIN preguntas p ON e.id = p.id_evaluacion
                WHERE r_mentor.nombre = 'ROLE_MENTOR'
                  AND e.activo = true
                  AND (
                      :filter IS NULL OR :filter = '' OR
                      LOWER(e.titulo) LIKE LOWER(CONCAT('%', :filter, '%')) OR
                      LOWER(CONCAT(mentor.nombre, ' ', mentor.apellidos)) LIKE LOWER(CONCAT('%', :filter, '%'))
                  )
                GROUP BY 
                    e.id, 
                    e.titulo, 
                    e.descripcion,
                    mentor.nombre, 
                    mentor.apellidos, 
                    e.tags, 
                    ee.completado, 
                    ee.id
                ORDER BY e.titulo
            """, nativeQuery = true)
    Page<EvaluacionResumen> findEvaluacionesResumen(
            @Param("emailEstudiante") String emailEstudiante,
            @Param("filter") String filter,
            Pageable pageable);


    @Query("""
            SELECT e FROM EvaluacionesEntity e
            WHERE e.mentor.email = :email
            AND(
                :filter IS NULL OR
                LOWER(e.titulo) LIKE LOWER(CONCAT('%', :filter, '%')) OR
                LOWER(e.tags) LIKE LOWER(CONCAT('%', :filter, '%'))
            )
            """)
    Page<EvaluacionesEntity> findByMentorIdWithFilter(
            @Param("email") String email,
            @Param("filter") String filter,
            Pageable pageable);
}
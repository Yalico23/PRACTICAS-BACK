package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.EntrevistaResumen;
import com.zonatech.app.infrastructure.entities.EntrevistaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrevistaEntityRepository extends JpaRepository<EntrevistaEntity, Long> {
    @Query("""
            SELECT e FROM EntrevistaEntity e
            WHERE e.mentor.email = :email
            AND(
                :filter IS NULL OR
                LOWER(e.titulo) LIKE LOWER(CONCAT('%', :filter, '%')) OR
                LOWER(e.descripcion) LIKE LOWER(CONCAT('%', :filter, '%'))
            )
            """)
    Page<EntrevistaEntity> findByMentorIdWithFilter(@Param("email") String email, @Param("filter") String filter, Pageable pageable);

    @Query(value = """
            SELECT
                e.id,
                e.titulo,
                e.descripcion,
                CONCAT(mentor.nombre, ' ', mentor.apellidos) as mentor,
                CASE
                    WHEN ee.completado = TRUE THEN 'Completado'
                    WHEN ee.id IS NOT NULL AND ee.completado = FALSE THEN 'En Progreso'
                    ELSE 'Disponible'
                END as estado,
                CONCAT(
                    FLOOR(SUM(p.tiempo) / 60), 'h ',
                    SUM(p.tiempo) % 60, ' min'
                ) as tiempo,
                ee.feed_back as feedBack
            FROM entrevistas e
            JOIN usuarios mentor ON e.id_mentor = mentor.id
            JOIN users_roles ur_mentor ON mentor.id = ur_mentor.user_id
            JOIN roles r_mentor ON ur_mentor.role_id = r_mentor.id
            LEFT JOIN entrevista_estudiantes ee ON e.id = ee.id_entrevista
                AND ee.id_estudiante = (
                    SELECT u_estudiante.id
                    FROM usuarios u_estudiante
                    JOIN users_roles ur_estudiante ON u_estudiante.id = ur_estudiante.user_id
                    JOIN roles r_estudiante ON ur_estudiante.role_id = r_estudiante.id
                    WHERE u_estudiante.email = :emailEstudiante
                        AND r_estudiante.nombre = 'ROLE_ESTUDIANTE'
                    LIMIT 1
                )
            LEFT JOIN pregunta_entrevistas p ON e.id = p.id_entrevista
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
                mentor.nombre,
                mentor.apellidos,
                ee.completado,
                ee.id
            """, nativeQuery = true)
    Page<EntrevistaResumen> findEntrevistasResumen
    (@Param("emailEstudiante") String emailEstudiante,
     @Param("filter") String filter,
     Pageable pageable);

    List<EntrevistaEntity> findByMentorEmail(String email);
}
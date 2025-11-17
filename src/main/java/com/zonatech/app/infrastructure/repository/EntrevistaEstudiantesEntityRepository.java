package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.ProgresoMensualEstudiante;
import com.zonatech.app.domain.models.ResponseDtoEntrevistaPendientes;
import com.zonatech.app.domain.models.TopEntrevistasEstudiantes;
import com.zonatech.app.infrastructure.entities.EntrevistaEstudiantesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    @Query(value = """
            SELECT\s
                u.id as estudianteId,
                u.nombre,
                u.apellidos,
                u.email,
                COUNT(DISTINCT ee.id) as totalSesiones,
                COUNT(DISTINCT CASE WHEN ee.completado = true THEN ee.id END) as sesionesCompletadas,
                ROUND(AVG(ee.valoracion), 2) as notaPromedio
            FROM usuarios u
            INNER JOIN entrevista_estudiantes ee ON u.id = ee.id_estudiante
            INNER JOIN entrevistas e ON ee.id_entrevista = e.id
            LEFT JOIN evaluacion_estudiante ev ON u.id = ev.id_estudiante\s
                AND e.id = ev.id_evaluacion
            WHERE e.id_mentor = :idMentor and ee.valoracion > 0
            GROUP BY u.id, u.nombre, u.apellidos, u.email
            HAVING COUNT(DISTINCT ee.id) > 0
            ORDER BY notaPromedio DESC;
            """, nativeQuery = true)
    List<TopEntrevistasEstudiantes> getTopEntrevistasEstudiantes(
            @Param("idMentor") Long idMentor);
}
package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.*;
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

    EvaluacionEstudianteEntity findByEvaluacionIdAndEstudianteId(Long evaluacionId, Long estudianteId);

    @Query(value = """
            SELECT\s
                u.nombre AS mentorNombre,
                u.apellidos AS mentorApellidos,
                COUNT(ee.id) AS evaluacionesConMentor,
                AVG(ee.calificacion_final) AS calificacionPromedio,
                MIN(ee.calificacion_final) AS calificacionMinima,
                MAX(ee.calificacion_final) AS calificacionMaxima
            FROM evaluacion_estudiante ee
            JOIN evaluaciones e ON ee.id_evaluacion = e.id
            JOIN usuarios u ON e.id_mentor = u.id
            WHERE ee.id_estudiante = :idEstudiante and ee.calificacion_final > 0
            GROUP BY u.id, u.nombre, u.apellidos
            ORDER BY calificacionPromedio DESC;
            """, nativeQuery = true)
    List<ComparacionMentores> getComparacionMentores(
            @Param("idEstudiante") Long idEstudiante);

    @Query(value = """
            SELECT\s
                e.titulo AS evaluacion,
                ee.calificacion_final AS miCalificacion,
                AVG(ee_todos.calificacion_final) AS calificacionPromedioGeneral,
                ee.calificacion_final - AVG(ee_todos.calificacion_final) AS diferenciaConPromedio,
                CASE\s
                    WHEN ee.calificacion_final > AVG(ee_todos.calificacion_final) THEN 'Por encima del promedio'
                    WHEN ee.calificacion_final = AVG(ee_todos.calificacion_final) THEN 'En el promedio'
                    ELSE 'Por debajo del promedio'
                END AS posicionRelativa
            FROM evaluacion_estudiante ee
            JOIN evaluaciones e\s
                ON ee.id_evaluacion = e.id
            JOIN evaluacion_estudiante ee_todos\s
                ON e.id = ee_todos.id_evaluacion
                AND ee_todos.calificacion_final > 0  
            WHERE ee.id_estudiante = :idEstudiante
                AND ee.calificacion_final > 0        
            GROUP BY e.id, e.titulo, ee.calificacion_final;
            """, nativeQuery = true)
    List<ComparacionPromedioGeneralEvalu> getComparacionPromedioGeneralEvalu(
            @Param("idEstudiante") Long idEstudiante);

    @Query(value = """
            SELECT\s
                DATE_FORMAT(ee.fecha_inicio, '%Y-%m') AS mesAnio,
                COUNT(ee.id) AS evaluacionesRealizadas,
                AVG(ee.calificacion_final) AS calificacionPromedio,
                MIN(ee.calificacion_final) AS calificacionMinima,
                MAX(ee.calificacion_final) AS calificacionMaxima
            FROM evaluacion_estudiante ee
            WHERE ee.id_estudiante = :idEstudiante
                AND ee.calificacion_final > 0   -- ← Excluir calificaciones en 0
            GROUP BY DATE_FORMAT(ee.fecha_inicio, '%Y-%m')
            ORDER BY mesAnio;
            """, nativeQuery = true)
    List<ProgresoMensualEstudiante> getProgresoMensualEstudiantes(
            @Param("idEstudiante") Long idEstudiante);

    @Query(value = """
            SELECT\s
                e.titulo AS evaluacion,
                e.descripcion,
                COUNT(DISTINCT ee.id_estudiante) AS totalEstudiantesAsignados,
                AVG(ee.calificacion_final) AS calificacionPromedio,
                MIN(ee.calificacion_final) AS calificacionMinima,
                MAX(ee.calificacion_final) AS calificacionMaxima,
                SUM(CASE WHEN ee.completado = 1 THEN 1 ELSE 0 END) AS completados
                FROM evaluaciones e
            LEFT JOIN evaluacion_estudiante ee ON e.id = ee.id_evaluacion
            WHERE e.id_mentor = :idMentor\s
            GROUP BY e.id, e.titulo, e.descripcion
            ORDER BY e.fecha_creacion DESC;
            """, nativeQuery = true)
    List<ResumenEvalucionMentor> getResumenEvalucionMentors(
            @Param("idMentor") Long idMentor);

    @Query(value = """
            SELECT\s
                u.nombre,
                u.apellidos,
                u.email,
                COUNT(ee.id) AS evaluacionesRealizadas,
                AVG(ee.calificacion_final) AS calificacionPromedio,
                SUM(CASE WHEN ee.completado = 1 THEN 1 ELSE 0 END) AS evaluacionesCompletadas
            FROM usuarios u
            JOIN evaluacion_estudiante ee ON u.id = ee.id_estudiante
            JOIN evaluaciones e ON ee.id_evaluacion = e.id
            WHERE e.id_mentor = :idMentor
            GROUP BY u.id, u.nombre, u.apellidos, u.email
            ORDER BY calificacionPromedio DESC;
            """, nativeQuery = true)
    List<MejorPeorDesempeno> getMejorPeorDesempenos(
            @Param("idMentor") Long idMentor);
}
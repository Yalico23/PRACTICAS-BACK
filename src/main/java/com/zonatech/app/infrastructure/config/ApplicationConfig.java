package com.zonatech.app.infrastructure.config;

import com.zonatech.app.application.services.*;
import com.zonatech.app.application.usecases.entrevistaEstudiante.EvaluarEntrevistaEstudianteUseCaseImpl;
import com.zonatech.app.application.usecases.entrevistaEstudiante.GenerarResumenIAUseCaseImpl;
import com.zonatech.app.application.usecases.entrevistaEstudiante.ListarEntrevistaEstudianteUseCaseImpl;
import com.zonatech.app.application.usecases.entrevistaEstudiante.ResponderEntrevistaUseCaseImpl;
import com.zonatech.app.application.usecases.entrevista.*;
import com.zonatech.app.application.usecases.evaluacion.*;
import com.zonatech.app.application.usecases.evaluacionEstudiante.CalificarEvaluacionUseCaseImpl;
import com.zonatech.app.application.usecases.evaluacionEstudiante.ListEvaluacionesEstudianteByIdEvaluacionUseCaseImpl;
import com.zonatech.app.application.usecases.evaluacionEstudiante.ResponserEvaluacionUseCaseImpl;
import com.zonatech.app.application.usecases.usuario.*;
import com.zonatech.app.domain.ports.output.*;
import com.zonatech.app.infrastructure.adapters.AwsServiceAdapter;
import com.zonatech.app.infrastructure.adapters.EmailServiceAdapter;
import com.zonatech.app.infrastructure.adapters.IAServiceAdapter;
import com.zonatech.app.infrastructure.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.util.Properties;

@Configuration
public class ApplicationConfig {

    // nota : Usuario
    @Bean
    public UsuarioRepositoryPort usuarioRepositoryPort(UsuarioEntityAdapters usuarioEntityAdapters) {
        return usuarioEntityAdapters;
    }

    @Bean
    public RolesRepositoryPort rolesRepositoryPort(RolesEntityAdapter rolesEntityAdapter) {
        return rolesEntityAdapter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UsuarioServices usuarioServices(UsuarioRepositoryPort usuarioRepositoryPort,
                                           RolesRepositoryPort rolesRepositoryPort,
                                           PasswordEncoder passwordEncoder,
                                           EmailServiceExternalServicePort emailServiceExternalServicePort,
                                           @Value("${app.host.url}") String URL) {
        return new UsuarioServices(
                new ListarUsuariosUseCaseImpl(usuarioRepositoryPort),
                new EncontraPorCorreoUseCaseImpl(usuarioRepositoryPort),
                new CrearUsuarioUseCaseImpl(usuarioRepositoryPort, rolesRepositoryPort, passwordEncoder),
                new RestablecerPasswordUseCaseImpl(usuarioRepositoryPort, emailServiceExternalServicePort, URL, passwordEncoder),
                new EncontrarUsuarioUseCaseImpl(usuarioRepositoryPort)
        );
    }

    // nota : Evaluaciones

    @Bean
    public EvaluacionesRepositoryPort evaluacionesRepositoryPort(EvaluacionEntityAdapters evaluacionEntityAdapters) {
        return evaluacionEntityAdapters;
    }

    @Bean
    public EvaluacionServices evaluacionServices
            (EvaluacionesRepositoryPort evaluacionesRepositoryPort,
             EvaluacionEstudianteRespositoryPort evaluacionEstudianteRespositoryPort) {
        return new EvaluacionServices(
                new CrearEvaluacionUseCaseImpl(evaluacionesRepositoryPort),
                new ListarEvaluacionesUseCaseImpl(evaluacionesRepositoryPort),
                new CambiarEstadoEvaluacionUseCaseImpl(evaluacionesRepositoryPort),
                new EliminarEvaluacionUseCaseImpl(evaluacionesRepositoryPort, evaluacionEstudianteRespositoryPort),
                new EditarEvaluacionUseCaseImpl(evaluacionesRepositoryPort,
                        evaluacionEstudianteRespositoryPort)
        );
    }

    // nota : Evaluaciones Estudiante

    @Bean
    public EvaluacionEstudianteRespositoryPort evaluacionEstudianteRespositoryPort(EvaluacionEstudianteEntityAdapter evaluacionEstudianteEntityAdapter) {
        return evaluacionEstudianteEntityAdapter;
    }

    @Bean
    public EvaluacionEstudianteServices evaluacionEstudianteServices
            (EvaluacionEstudianteRespositoryPort evaluacionEstudianteRespositoryPort, IAServiceExternalServicePort iaServiceExternalServicePort) {
        return new EvaluacionEstudianteServices(
                new ResponserEvaluacionUseCaseImpl(evaluacionEstudianteRespositoryPort),
                new ListEvaluacionesEstudianteByIdEvaluacionUseCaseImpl(evaluacionEstudianteRespositoryPort),
                new CalificarEvaluacionUseCaseImpl(evaluacionEstudianteRespositoryPort, iaServiceExternalServicePort)
        );
    }

    // nota : Entrevista

    @Bean
    public EntrevistaRepositoryPort entrevistaRepositoryPort(EntrevistaEntityAdapter entrevistaEntityAdapter) {
        return entrevistaEntityAdapter;
    }

    @Bean
    public EntrevistaServices entrevistaServices
            (EntrevistaRepositoryPort entrevistaRepositoryPort,
             EntrevistaEstudianteRepositoryPort entrevistaEstudianteRepositoryPort) {
        return new EntrevistaServices(
                new CrearEntrevistaUseCaseImpl(entrevistaRepositoryPort),
                new ModificarEntrevistaUseCaseImpl(entrevistaRepositoryPort),
                new EliminarEntrevistaUseCaseImpl(entrevistaRepositoryPort, entrevistaEstudianteRepositoryPort),
                new ListarEntrevistasUseCaseImpl(entrevistaRepositoryPort),
                new CambiarEstadoEntrevistaUseCaseImpl(entrevistaRepositoryPort)
        );
    }

    // nota : Entrevista Estudiante
    @Bean
    public EntrevistaEstudianteRepositoryPort entrevistaEstudianteRepositoryPort
            (EntrevistaEstudianteEntityAdapter entrevistaEstudianteEntityAdapter) {
        return entrevistaEstudianteEntityAdapter;
    }

    @Bean
    public EntrevistaEstudianteServices entrevistaEstudianteServices
            (EntrevistaEstudianteRepositoryPort entrevistaEstudianteRepositoryPort,
             AwsServiceExternalServicePort awsServiceExternalServicePort,
             IAServiceExternalServicePort iaServiceExternalServicePort) {
        return new EntrevistaEstudianteServices(
                new ResponderEntrevistaUseCaseImpl (awsServiceExternalServicePort, entrevistaEstudianteRepositoryPort , bucket, iaServiceExternalServicePort),
                new ListarEntrevistaEstudianteUseCaseImpl
                        (entrevistaEstudianteRepositoryPort, awsServiceExternalServicePort, bucket),
                new GenerarResumenIAUseCaseImpl(iaServiceExternalServicePort),
                new EvaluarEntrevistaEstudianteUseCaseImpl(entrevistaEstudianteRepositoryPort)
        );
    }

    // nota : EmailService
    @Bean
    public EmailServiceExternalServicePort emailServiceExternalServicePort(JavaMailSender javaMailSender){
        return new EmailServiceAdapter(javaMailSender);
    }

    @Bean
    public JavaMailSender javaMailSender(
            @Value("${spring.mail.host}") String host,
            @Value("${spring.mail.port}") int port,
            @Value("${spring.mail.username}") String username,
            @Value("${spring.mail.password}") String password,
            @Value("${spring.mail.protocol:smtp}") String protocol) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setProtocol(protocol);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.starttls.required", true);
        props.put("mail.smtp.connectiontimeout", 10000);
        props.put("mail.smtp.timeout", 10000);
        props.put("mail.smtp.writetimeout", 10000);
        // Para debugging (puedes remover después)
        props.put("mail.debug", "true");

        return mailSender;
    }

    // nota : IAService
    @Bean
    WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }

    @Bean
    public IAServiceExternalServicePort iaServiceExternalServicePort(WebClient webClient){
        return new IAServiceAdapter(webClient);
    }

    // nota : Servicio Amazon S3 Service

    @Value("${aws.access-key-id}")
    private String awsAccessKeyId;
    @Value("${aws.secret-key}")
    private String awsSecretKey;
    @Value("${aws.region}")
    private String awsRegion;
    @Value("${aws.s3.bucket}")
    private String bucket;

    @Bean
    public S3Client getS3Client() {
        AwsCredentials basicAwsCredential = AwsBasicCredentials.create(awsAccessKeyId, awsSecretKey);
        return S3Client.builder()
                .region(Region.of(awsRegion))
                .endpointOverride(URI.create("https://s3.us-east-2.amazonaws.com"))
                .credentialsProvider(StaticCredentialsProvider.create(basicAwsCredential))
                .build();
    }

    @Bean
    public S3Presigner getS3Presigner(){
        AwsCredentials basicCredentials = AwsBasicCredentials.create(awsAccessKeyId,awsSecretKey);
                return S3Presigner.builder()
                        .credentialsProvider(StaticCredentialsProvider.create(basicCredentials))
                        .region(Region.of(awsRegion))
                        .build();
    }

    @Bean
    public AwsServiceExternalServicePort awsServiceExternalServicePort
            (S3Client getS3Client, S3Presigner getS3Presigner){
        return new AwsServiceAdapter(getS3Client, getS3Presigner);
    }
}

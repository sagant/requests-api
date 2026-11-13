package uy.requests.config;

import java.util.List;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uy.requests.domain.RequestEntity;
import uy.requests.domain.RequestType;
import uy.requests.persistence.RequestRepository;

/** Datos iniciales que se cargan cuando está activo el perfil local. */
@Configuration
@Profile("local")
public class LocalSampleData {
    @Bean
    ApplicationRunner populateRequests(RequestRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            repository.saveAll(List.of(
                    new RequestEntity("Monitor para puesto nuevo", "Se necesita un monitor para el nuevo puesto de trabajo", RequestType.EQUIPMENT),
                    new RequestEntity("Acceso a repositorio", "Solicito acceso al repositorio del equipo de desarrollo", RequestType.ACCESS),
                    new RequestEntity("Mouse ergonomico", "Necesito reemplazar el mouse por uno ergonomico", RequestType.EQUIPMENT),
                    new RequestEntity("Alta en plataforma", "Solicito habilitacion de acceso a la plataforma interna", RequestType.ACCESS),
                    new RequestEntity("Reparacion de escritorio", "Se solicita revision del escritorio del sector", RequestType.OTHER),
                    new RequestEntity("Teclado para oficina", "Necesito un teclado para el puesto de atencion", RequestType.EQUIPMENT),
                    new RequestEntity("Acceso a tablero", "Solicito acceso al tablero de reportes de la unidad", RequestType.ACCESS),
                    new RequestEntity("Material para reunion", "Se requiere material para organizar una reunion", RequestType.OTHER)
            ));
        };
    }
}

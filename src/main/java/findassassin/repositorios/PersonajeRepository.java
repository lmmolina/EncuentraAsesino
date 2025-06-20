package findassassin.repositorios;

import findassassin.modelos.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonajeRepository extends JpaRepository<Personaje, Integer> {
}

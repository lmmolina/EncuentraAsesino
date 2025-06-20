package findassassin.repositorios;

import findassassin.modelos.Asesino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsesinoRepository extends JpaRepository<Asesino, Integer> {
}

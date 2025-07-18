package findassassin.repositorios;

import findassassin.modelos.Asesino;
import findassassin.modelos.Filtros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FiltrosRepository extends JpaRepository<Filtros, Long> {
}

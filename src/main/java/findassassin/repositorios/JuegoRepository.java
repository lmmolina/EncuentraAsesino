package findassassin.repositorios;

import findassassin.modelos.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JuegoRepository extends JpaRepository<Juego, Integer> {
    @Query("SELECT j FROM Juego j WHERE j.ganador IS NULL")
    List<Juego> obtenerJuegosNoTerminados();
}

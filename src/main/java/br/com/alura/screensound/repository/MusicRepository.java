package br.com.alura.screensound.repository;

import br.com.alura.screensound.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Artista, Long> {
}

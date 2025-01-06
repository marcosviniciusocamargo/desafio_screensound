package br.com.alura.screensound.principal;

import br.com.alura.screensound.model.Artista;
import br.com.alura.screensound.model.Musica;
import br.com.alura.screensound.repository.MusicRepository;
import br.com.alura.screensound.service.ConsultaGoogleGemini;
import br.com.alura.screensound.service.GetUserInput;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class Principal {

    private final MusicRepository repository;
    private final GetUserInput getUserInput = new GetUserInput();

    public Principal(MusicRepository repositorio) {
        this.repository = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0){
            opcao = Integer.parseInt(getUserInput.get("""
                    *** Screen Sound Music ***
                    O que deseja fazer?
                    1 - Cadastrar artistas
                    2 - Cadastrar músicas
                    3 - Listar músicas
                    4 - Buscar músicas por artistas
                    5 - Pesquisar dados sobre um artista
                    
                    0 - Sair
                    """));
            switch (opcao){
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    listarMusicasPorArtistas();
                    break;
                case 5:
                    pesquisarArtista();
                    break;
                default:
                    System.exit(200);
            }
        }

    }

    private void cadastrarArtistas() {
        var opcao = 1;
        while (opcao == 1){
            var nome = getUserInput.get("Qual o nome do artista?");
            var tipo = getUserInput.get("Qual o tipo do artista? (solo, dupla, banda)");
            repository.save(new Artista(nome, tipo));
            opcao = Integer.parseInt(getUserInput.get("""
                    Artista Cadastrado!
                    Deseja cadastrar outro artista?
                    1 - Sim
                    2 - Não
                    """));
        }
    }

    private void cadastrarMusicas() {
        var opcao = 1;
        List<Artista> artistas = repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        artistas.forEach(a -> System.out.println(a.getNome()));
        var nomeArtista = getUserInput.get("Qual o nome do artista?");
        Optional<Artista> artista = artistas.stream()
                .filter(a -> a.getNome().toLowerCase().contains(nomeArtista.toLowerCase()))
                .findFirst();
        if (artista.isPresent()){
            Artista artistaEncontrado = artista.get();
            while (opcao == 1){
                var nome = getUserInput.get("Qual o nome da música?");
                artistaEncontrado.getMusicas().add(new Musica(nome, artistaEncontrado));
                opcao = Integer.parseInt(getUserInput.get("""
                    Música Cadastrada!
                    Deseja cadastrar outra música?
                    1 - Sim
                    2 - Não
                    """));
            }
            repository.save(artistaEncontrado);
        }
    }

    private void listarMusicas() {
        List<Artista> listaDeArtistas = repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        listaDeArtistas.forEach(a -> System.out.println("Artista: " + a.getNome() + "\nMúsicas: "
                + a.getMusicas()));
    }

    private void listarMusicasPorArtistas() {
        List<Artista> artistas = repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        artistas.forEach(a -> System.out.println(a.getNome()));
        var nomeArtista = getUserInput.get("Qual o nome do artista?");
        Optional<Artista> artista = artistas.stream()
                .filter(a -> a.getNome().toLowerCase().contains(nomeArtista.toLowerCase()))
                .findFirst();
        if (artista.isPresent()){
            Artista artistaEncontrado = artista.get();
            System.out.println(artistaEncontrado.getMusicas());
        }
    }

    private void pesquisarArtista() {
        var nomeArtista = getUserInput.get("Qual o nome do artista que deseja pesquisar? ");
        System.out.println(ConsultaGoogleGemini.consulta(nomeArtista));
    }

}

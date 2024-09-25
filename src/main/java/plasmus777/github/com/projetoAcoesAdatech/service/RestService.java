package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RestService<T>{

    public List<T> obterLista();
    public Optional<T> obter(Long id);
    public ResponseEntity<String> atualizar(Long id, T t);
    public ResponseEntity<String> cadastrar(T t);
    public ResponseEntity<String> apagar(Long id);

}

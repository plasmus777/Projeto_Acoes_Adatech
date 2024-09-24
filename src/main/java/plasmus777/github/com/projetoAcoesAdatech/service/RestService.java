package plasmus777.github.com.projetoAcoesAdatech.service;

import java.util.List;
import java.util.Optional;

public interface RestService<T>{

    public List<T> obterLista();
    public Optional<T> obter(Long id);
    public boolean atualizar(Long id, T t);
    public boolean cadastrar(T t);
    public boolean apagar(Long id);

}

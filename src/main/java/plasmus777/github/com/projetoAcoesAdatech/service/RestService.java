package plasmus777.github.com.projetoAcoesAdatech.service;

import java.util.List;

public interface RestService<T>{

    public List<T> obterLista();
    public T obter(Long id);
    public boolean atualizar(Long id, T t);
    public boolean cadastrar(T t);
    public boolean apagar(Long id);

}

package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;

import java.util.List;
import java.util.Optional;

@Service
public class AcaoService implements RestService<Acao> {


    @Override
    public List<Acao> obterLista() {
        return List.of();
    }

    @Override
    public Optional<Acao> obter(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean atualizar(Long id, Acao acao) {
        return false;
    }

    @Override
    public boolean cadastrar(Acao acao) {
        return false;
    }

    @Override
    public boolean apagar(Long id) {
        return false;
    }
}

package plasmus777.github.com.projetoAcoesAdatech.service;

import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;

import java.util.List;
import java.util.Optional;

public class RendaFixaService implements RestService<RendaFixa>{

    @Override
    public List<RendaFixa> obterLista() {
        return List.of();
    }

    @Override
    public Optional<RendaFixa> obter(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean atualizar(Long id, RendaFixa rendaFixa) {
        return false;
    }

    @Override
    public boolean cadastrar(RendaFixa rendaFixa) {
        return false;
    }

    @Override
    public boolean apagar(Long id) {
        return false;
    }
}

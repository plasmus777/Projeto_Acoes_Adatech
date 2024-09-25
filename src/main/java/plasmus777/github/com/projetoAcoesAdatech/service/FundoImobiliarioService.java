package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;

import java.util.List;
import java.util.Optional;

@Service
public class FundoImobiliarioService implements RestService<FundoImobiliario> {

    @Override
    public List<FundoImobiliario> obterLista() {
        return List.of();
    }

    @Override
    public Optional<FundoImobiliario> obter(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean atualizar(Long id, FundoImobiliario fundoImobiliario) {
        return false;
    }

    @Override
    public boolean cadastrar(FundoImobiliario fundoImobiliario) {
        return false;
    }

    @Override
    public boolean apagar(Long id) {
        return false;
    }
}

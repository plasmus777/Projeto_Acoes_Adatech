package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> atualizar(Long id, FundoImobiliario fundoImobiliario) {
        return ResponseEntity.status(HttpStatus.OK).body("Código executado com sucesso.");
    }

    @Override
    public ResponseEntity<String> cadastrar(FundoImobiliario fundoImobiliario) {
        return ResponseEntity.status(HttpStatus.OK).body("Código executado com sucesso.");
    }

    @Override
    public ResponseEntity<String> apagar(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("Código executado com sucesso.");
    }
}

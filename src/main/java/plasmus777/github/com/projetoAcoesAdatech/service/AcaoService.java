package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> atualizar(Long id, Acao acao) {
        return ResponseEntity.status(HttpStatus.OK).body("Código executado com sucesso.");
    }

    @Override
    public ResponseEntity<String> cadastrar(Acao acao) {
        return ResponseEntity.status(HttpStatus.OK).body("Código executado com sucesso.");
    }

    @Override
    public ResponseEntity<String> apagar(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("Código executado com sucesso.");
    }
}

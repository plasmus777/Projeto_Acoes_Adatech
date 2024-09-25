package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;

import java.util.List;
import java.util.Optional;

@Service
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
    public ResponseEntity<String> atualizar(Long id, RendaFixa rendaFixa) {
        return ResponseEntity.status(HttpStatus.OK).body("Código executado com sucesso.");
    }

    @Override
    public ResponseEntity<String> cadastrar(RendaFixa rendaFixa) {
        return ResponseEntity.status(HttpStatus.OK).body("Código executado com sucesso.");
    }

    @Override
    public ResponseEntity<String> apagar(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("Código executado com sucesso.");
    }
}

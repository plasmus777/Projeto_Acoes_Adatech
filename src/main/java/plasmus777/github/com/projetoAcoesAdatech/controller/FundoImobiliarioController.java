package plasmus777.github.com.projetoAcoesAdatech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;
import plasmus777.github.com.projetoAcoesAdatech.service.FundoImobiliarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/fundosimobiliarios")
public class FundoImobiliarioController {

    private final FundoImobiliarioService fundoImobiliarioService;

    public FundoImobiliarioController(FundoImobiliarioService fundoImobiliarioService){
        this.fundoImobiliarioService = fundoImobiliarioService;
    }

    @GetMapping()
    public List<FundoImobiliario> obterFundosImobiliarios() {
        return fundoImobiliarioService.obterLista();
    }

    @GetMapping("/{id}")
    public FundoImobiliario obterFundoImobiliario(@PathVariable Long id){
        Optional<FundoImobiliario> opt = fundoImobiliarioService.obter(id);

        return opt.orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarFundoImobiliario(@PathVariable Long id, @RequestBody FundoImobiliario novoFundoImobiliario){
        return fundoImobiliarioService.atualizar(id, novoFundoImobiliario);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarFundoImobiliario(@RequestBody FundoImobiliario fundoImobiliario){
        return fundoImobiliarioService.cadastrar(fundoImobiliario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarFundoImobiliario(@PathVariable Long id){
        return fundoImobiliarioService.apagar(id);
    }
}
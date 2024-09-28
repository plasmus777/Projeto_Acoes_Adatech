package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AtivoApi;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.Relatorio;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.SearchAtivoApi;

import java.util.List;
import java.util.Optional;

@Service
public class RelatorioService {

    private FinnhubClient finnhubClient;

    public RelatorioService(FinnhubClient finnhubClient){
        this.finnhubClient = finnhubClient;
    }

    public Optional<Relatorio> gerarRelatorio(String codigoAtivo){
        Relatorio relatorio;
        if(codigoAtivo != null && !(codigoAtivo.isBlank())){
            SearchAtivoApi searchAtivoApi = finnhubClient.buscarInformacoesAtivos(codigoAtivo);
            AtivoApi ativoApi = null;
            List<AtivoApi> listaAtivos = searchAtivoApi.getResult();
            if(listaAtivos != null && !(listaAtivos.isEmpty())){
                for(AtivoApi a: listaAtivos){
                    if(a.getSymbol().equals(codigoAtivo)){
                        ativoApi = a;
                        break;
                    }
                }

                AcaoApi acaoApi = finnhubClient.buscarInformacoesAtivo(codigoAtivo);
                if(acaoApi != null && ativoApi != null){
                    relatorio = new Relatorio(ativoApi, acaoApi);
                    return Optional.of(relatorio);
                }
            }
        }

        return Optional.empty();
    }

}

package br.com.alura.service.http;

import br.com.alura.domain.Agencia;
import br.com.alura.domain.http.AgenciaHttp;
import br.com.alura.domain.http.SituacaoCadastral;
import br.com.alura.exceptions.AgenciaNaoAtivaOuNaoEncontradaException;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AgenciaService {

  @RestClient
  private SituacaoCadastrolHttpService situacaoCadastrolHttpService;


  private List<Agencia> agencias = new ArrayList<>();

  public void cadastrar(Agencia agencia) {

    AgenciaHttp agenciaHttp = situacaoCadastrolHttpService.buscarPorCnpj(agencia.getCnpj());
    if (agenciaHttp != null && agenciaHttp.getSituacaoCadastral().equals(SituacaoCadastral.ATIVO)) {
      agencias.add(agencia);
    } else {
      throw new AgenciaNaoAtivaOuNaoEncontradaException();
    }


  }


  public Agencia buscarPorId(Integer id) {
    return agencias.stream().filter(agencia -> agencia.getId().equals(id)).toList().getFirst();
  }


  public void deleter(Integer id) {
    agencias.removeIf(agencia -> agencia.getId().equals(id));
  }


  public void alterar(Agencia agencia) {
    deleter(agencia.getId());
    cadastrar(agencia);
  }


}

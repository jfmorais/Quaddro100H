package br.com.quaddro.quaddro100h.lab04.app.ws;

import br.com.quaddro.quaddro100h.lab04.app.dto.EnderecoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EnderecoEndpoint {

    @GET("cep/{valor}")
    Call<EnderecoDTO> consultaCEP(@Path("valor") String valor);
}

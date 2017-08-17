package br.com.quaddro.quaddro100h.lab04.app.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import br.com.quaddro.quaddro100h.lab04.app.dto.EnderecoDTO;
import br.com.quaddro.quaddro100h.lab04.app.util.RetrofitHelper;
import br.com.quaddro.quaddro100h.lab04.app.ws.EnderecoEndpoint;
import br.com.quaddro.quaddro100h.lab04.domain.model.Endereco;
import br.com.quaddro.quaddro100h.lab04.domain.model.UF;
import retrofit2.Call;
import retrofit2.Response;

public class ConsultarCEPService extends IntentService {

    private EnderecoEndpoint endpoint;

    public ConsultarCEPService() {
        super("ConsultarCEPService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        endpoint = RetrofitHelper.with(this).createEnderecoEndpoint();

        Log.i("LAB", "Passei pelo onCreate em: " + getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("LAB", "Passei pelo onDestroy em: " + getClass().getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent resp = new Intent("banana");
        if (intent != null) {
            try {
                String cep = intent.getStringExtra("cep");
                Call<EnderecoDTO> call = endpoint.consultaCEP(cep);
                Response<EnderecoDTO> response = call.execute();

                if (response.isSuccessful()) {
                    EnderecoDTO dto = response.body();
                    if (dto != null) {
                        // FIXME Arrumar lógica para dentro do model e do dto.
                        int index = dto.getLogradouro().indexOf(' ');
                        String tipo = dto.getLogradouro().substring(0, index);
                        String nome = dto.getLogradouro().substring(index + 1);
                        Endereco model = Endereco.of(tipo, nome,
                                "", "",
                                cep,
                                dto.getBairro(),
                                dto.getCidade(),
                                UF.valueOf(dto.getEstado()));

                        resp.putExtra("model", model);
                        resp.putExtra("encontrou", Boolean.TRUE);
                    }
                } else {
                    resp.putExtra("encontrou", Boolean.FALSE);
                }

                Log.i("LAB", "Passei pelo serviço...");
                Log.d("LAB", "CEP: " + cep);
            } catch (Exception cause) {
                Log.e("LAB", cause.getMessage(), cause);
            }
        }

        sendBroadcast(resp);
    }
}

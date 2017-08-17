package br.com.quaddro.quaddro100h.lab04.app.util;

import android.content.Context;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab04.app.ws.EnderecoEndpoint;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitHelper {

    private Context context;

    private Retrofit retrofit;

    private RetrofitHelper(Context context) {
        super();
        this.context = context;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.cep_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitHelper with(Context context) {
        return new RetrofitHelper(context);
    }

    public EnderecoEndpoint createEnderecoEndpoint() {
        return retrofit.create(EnderecoEndpoint.class);
    }
}

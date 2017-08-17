package br.com.quaddro.quaddro100h.lab04.app.controller;

import android.hardware.input.InputManager;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroActivity;
import br.com.quaddro.quaddro100h.lab04.app.dto.EnderecoDTO;
import br.com.quaddro.quaddro100h.lab04.app.util.RetrofitHelper;
import br.com.quaddro.quaddro100h.lab04.app.ws.EnderecoEndpoint;
import br.com.quaddro.quaddro100h.lab04.domain.model.CEP;
import br.com.quaddro.quaddro100h.lab04.domain.model.Endereco;
import br.com.quaddro.quaddro100h.lab04.domain.model.UF;
import br.com.quaddro.quaddro100h.lab04.repository.util.EnderecoSQLiteHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnderecoInserirActivity extends QuaddroActivity {

    EditText cepView, logradouroView, numeroView, complementoView,
            bairroView, municipioView;

    Spinner ufView;

    CEP cepModel;

    EnderecoEndpoint endpoint;

    EnderecoSQLiteHelper sqlite;

    TextView.OnEditorActionListener editorAction =
            new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            Log.i(TAG, "onEditor...");
            Endereco e = Endereco.of(
                    logradouroView.getText().toString(),
                    numeroView.getText().toString(),
                    complementoView.getText().toString(),
                    cepView.getText().toString(),
                    bairroView.getText().toString(),
                    municipioView.getText().toString(),
                    (UF) ufView.getSelectedItem());

            return validar(e);
        }
    };

    private boolean validar(Endereco e) {
        boolean ok = true;

        try {
            e.validarLogradouro();

        } catch (Exception cause) {
            logradouroView.setError(cause.getMessage());
            ok = false;
        }

        try {
            e.validarCep();
        } catch (Exception cause) {
            cepView.setError(cause.getMessage());
            ok = false;
        }

        try {
            e.validarBairro();
        } catch (Exception cause) {
            bairroView.setError(cause.getMessage());
            ok = false;
        }

        try {
            e.validarMunicipio();
        } catch (Exception cause) {
            municipioView.setError(cause.getMessage());
            ok = false;
        }

        return ok;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.endereco_inserir_view);

        sqlite = EnderecoSQLiteHelper.with(this);
        cepView = getViewById(R.id.cep);
        logradouroView = getViewById(R.id.logradouro);
        numeroView = getViewById(R.id.numero);
        complementoView = getViewById(R.id.complemento);
        bairroView = getViewById(R.id.bairro);
        municipioView = getViewById(R.id.municipio);
        ufView = getViewById(R.id.uf);
        cepModel = CEP.getInstance();
        endpoint = RetrofitHelper.with(this).createEnderecoEndpoint();

        municipioView.setOnEditorActionListener(editorAction);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sqlite.abrirDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sqlite.fecharDB();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ufView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                UF.values()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi = menu.add(R.string.lab04_endereco_salvar);

        mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        mi.setIcon(android.R.drawable.ic_menu_save);
        mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                salvarEndereco();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.i(TAG, "Endereço cancelado!");
        Toast.makeText(this, R.string.lab04_endereco_cancelado, Toast.LENGTH_LONG).show();
    }

    public void salvarEndereco() {
        int time = Toast.LENGTH_SHORT;
        try {
            // SQLite para inserir ou atualizar o endereço
            Endereco model = Endereco.of(logradouroView.getText().toString(),
                    numeroView.getText().toString(),
                    complementoView.getText().toString(),
                    cepView.getText().toString(),
                    bairroView.getText().toString(),
                    municipioView.getText().toString(),
                    (UF) ufView.getSelectedItem());
            if(validar(model)) {
                sqlite.salvar(model);
                Log.i(TAG, "Endereço salvo!");
                Toast.makeText(this, R.string.lab04_endereco_salvo, time).show();
                finish();
            }
        } catch (Exception cause) {
            Log.e(TAG, "OPS..", cause);
            Toast.makeText(this, "OPS...", time).show();
        }
    }

    public void consultarCEP(View v) {
        Toast toast;
        try {
            InputMethodManager im = (InputMethodManager)
                    getSystemService(INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(cepView.getWindowToken(), 0);

            cepModel.setValor(cepView.getText().toString());
            cepModel.validar();
            Log.i(TAG, "CEP válido!");
            toast = Toast.makeText(this,
                    R.string.lab04_endereco_consultar,
                    Toast.LENGTH_SHORT);
            // Chama na Web via Retrofit
            Call<EnderecoDTO> call = endpoint.consultaCEP(cepModel.getValor());

            call.enqueue(new Callback<EnderecoDTO>() {
                @Override
                public void onResponse(Call<EnderecoDTO> call, Response<EnderecoDTO> response) {
                    EnderecoDTO dto;

                    if (response.isSuccessful()) {
                        dto = response.body();

                        logradouroView.setText(dto.getLogradouro());
                        bairroView.setText(dto.getBairro());
                        municipioView.setText(dto.getCidade());
                        UF uf = UF.valueOf(dto.getEstado());
                        ufView.setSelection(uf.ordinal());
                        logradouroView.requestFocus();
                        logradouroView.selectAll();
                        Log.i(TAG, "Foi pra conta!");
                        Log.d(TAG, dto.toString());
                    }
                }

                @Override
                public void onFailure(Call<EnderecoDTO> call, Throwable t) {
                    Log.e(TAG, "DEU RUIM!!!");
                }
            });
            // TODO Preencher os campos do formulário
        } catch (Exception cause) {
            Log.e(TAG, "OPS... ", cause);
            toast = Toast.makeText(this, cause.getMessage(), Toast.LENGTH_LONG);
            cepView.requestFocus();
        }

        toast.show();
    }
}

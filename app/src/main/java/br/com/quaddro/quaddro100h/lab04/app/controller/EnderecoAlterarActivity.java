package br.com.quaddro.quaddro100h.lab04.app.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.databinding.EnderecoAlterarViewBinding;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroActivity;
import br.com.quaddro.quaddro100h.lab04.app.binding.EnderecoAction;
import br.com.quaddro.quaddro100h.lab04.domain.model.Endereco;
import br.com.quaddro.quaddro100h.lab04.domain.model.UF;
import br.com.quaddro.quaddro100h.lab04.repository.util.EnderecoSQLiteHelper;

public class EnderecoAlterarActivity extends QuaddroActivity {

    EnderecoAlterarViewBinding binding;

    EnderecoSQLiteHelper sqlite;

    BroadcastReceiver consultaCEP = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Passei pelo receiver...");
            Endereco model = (Endereco) intent.getSerializableExtra("model");
            if (model != null) {
                binding.setEndereco(model);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,
                R.layout.endereco_alterar_view);
        binding.setController(new EnderecoAction(binding));
        sqlite = EnderecoSQLiteHelper.with(this);
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

    Endereco model;

    @Override
    protected void onResume() {
        super.onResume();

        binding.uf.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, UF.values()));
        Long id = getIntent().getLongExtra("_id", 0L);

        if (model == null) {
            model = sqlite.recuperar(id);
            binding.setEndereco(model);
            binding.uf.setSelection(model.getUfOrdinal());
        }

        registerReceiver(consultaCEP, new IntentFilter("banana"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(consultaCEP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);

        inflater.inflate(R.menu.endereco_salvar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.endereco_salvar:
                    // SQLite para inserir ou atualizar o endereço
                    Endereco model = binding.getEndereco();
                    // model.validar();
                    sqlite.salvar(model);

                    Log.i(TAG, "Endereço salvo!");
                    Toast.makeText(this, R.string.lab04_endereco_salvo, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        } catch (Exception cause) {
            Log.e(TAG, "OPS...", cause);
            Toast.makeText(this, "OPS", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.i(TAG, "Endereço cancelado!");
        Toast.makeText(this, R.string.lab04_endereco_cancelado,
                Toast.LENGTH_LONG).show();
    }
}

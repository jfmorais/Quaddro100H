package br.com.quaddro.quaddro100h.lab04.app.controller;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroActivity;
import br.com.quaddro.quaddro100h.lab04.domain.model.Endereco;
import br.com.quaddro.quaddro100h.lab04.domain.model.UF;
import br.com.quaddro.quaddro100h.lab04.repository.util.EnderecoSQLiteHelper;

public class EnderecoListarActivity extends ListActivity {

    EnderecoSQLiteHelper sqlite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sqlite = EnderecoSQLiteHelper.with(this);
        registerForContextMenu(getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.endereco_contexto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.endereco_alterar:
                Log.i(QuaddroActivity.TAG, "Alterar endereço...");
                // Intent explícito
                Intent i = new Intent(this, EnderecoAlterarActivity.class);
                i.putExtra("_id", info.id);
                startActivity(i);
                break;
            case R.id.endereco_apagar:
                Log.i(QuaddroActivity.TAG, "Apagar endereço...");
                AlertDialog alert;

                alert = new AlertDialog.Builder(this)
                        .setTitle("CUIDADO")
                        .setMessage("Deseja apagar?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Log.i(QuaddroActivity.TAG, "Apagar endereço...");
                                    sqlite.apagar(info.id);
                                    onResume();
                                    Toast.makeText(getApplicationContext(),
                                            "Apagado!",
                                            Toast.LENGTH_SHORT).show();
                                } catch (Exception cause) {
                                    Log.e(QuaddroActivity.TAG, "OPS...", cause);
                                    Toast.makeText(getApplicationContext(),
                                            "OPS...",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }})
                        .setNegativeButton("Não", null)
                        .create();

                alert.show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.endereco_listar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.endereco_inserir:
                Intent i = new Intent("android.intent.action.endereco.INSERIR");

                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
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

        try {
            Cursor c = sqlite.listarTodosPorCursor();
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                    R.layout.endereco_item_view, c,
                    new String[] {
                            "tp_logradouro" , "nm_logradouro",
                            "nr_endereco", "ds_complemento", "nr_cep",
                            "nm_bairro", "nm_municipio", "nm_uf"},
                    new int[] {
                            R.id.logradouroTipo, R.id.logradouroNome,
                            R.id.numero, R.id.complemento, R.id.cep,
                            R.id.bairro, R.id.municipio, R.id.uf}, 0);

//            ArrayList<Endereco> list = sqlite.listarTodos();
//            ArrayAdapter<Endereco> adapter = new ArrayAdapter<>(this,
//                    android.R.layout.simple_list_item_1, list);
            setListAdapter(sca);
        } catch (Exception cause) {
            Log.e("LAB", "OPS...", cause);
            Toast.makeText(this, "OPS...", Toast.LENGTH_SHORT).show();
        }
    }
}

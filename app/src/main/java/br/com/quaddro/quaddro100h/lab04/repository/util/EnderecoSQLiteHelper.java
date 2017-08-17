package br.com.quaddro.quaddro100h.lab04.repository.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import br.com.quaddro.quaddro100h.lab04.domain.model.Endereco;
import br.com.quaddro.quaddro100h.lab04.domain.model.UF;

public class EnderecoSQLiteHelper extends SQLiteOpenHelper {

    private Context context;

    private SQLiteDatabase db;

    private EnderecoSQLiteHelper(Context context) {
        super(context, "enderecos.db", null, 1);

        this.context = context;
    }

    public static EnderecoSQLiteHelper with(Context context) {
        return new EnderecoSQLiteHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Ler script por arquivo via context
        db.execSQL("CREATE TABLE endereco (" +
                "_id Integer NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "tp_logradouro VARCHAR(50) NOT NULL, " +
                "nm_logradouro VARCHAR(80) NOT NULL, " +
                "nr_endereco VARCHAR(5) NULL, " +
                "ds_complemento VARCHAR(50) NULL, " +
                "nr_cep CHAR(9) NOT NULL, " +
                "nm_bairro VARCHAR(80) NOT NULL," +
                "nm_municipio VARCHAR(80) NOT NULL, " +
                "nm_uf CHAR(2) NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void salvar(Endereco endereco) throws Exception {
        ContentValues values = new ContentValues();
        values.put("tp_logradouro", endereco.getLogradouroTipo());
        values.put("nm_logradouro", endereco.getLogradouroNome());
        values.put("nr_endereco", endereco.getNumero());
        values.put("ds_complemento", endereco.getComplemento());
        values.put("nr_cep", endereco.getCep().getValor());
        values.put("nm_bairro", endereco.getBairroNome());
        values.put("nm_municipio", endereco.getMunicipioNome());
        values.put("nm_uf", endereco.getUf().toString());
        if (endereco.isNullId()) {
            long id = db.insert("endereco", null, values);
            Log.d("LAB", "Novo endereço com id: " + id);
        } else {
            int rows = db.update("endereco", values, "_id = ?", new String[] {
                    endereco.getId().toString()
            });
            Log.d("LAB", "Endereços atualizados: " + rows);
        }
    }

    public void apagar(Long id) throws Exception {
        int rows = db.delete("endereco", "_id = ?", new String[] {
                id.toString()
        });
        Log.d("LAB", "Endereços apagados: " + rows);
    }

    public void apagar(Endereco endereco) throws Exception {
        if (endereco.isNullId()) {
            throw new Exception("ID nulo!");
        }
        apagar(endereco.getId());
    }

    public Endereco recuperar(Long id) {
        Endereco e = null;
        try (Cursor c = db.query("endereco", null, "_id = ?",
                new String[] { id.toString()}, null, null, null)) {
            if (c.moveToFirst()) {
                e = createInstance(c);
            }
            return e;
        }
    }

    public ArrayList<Endereco> listarTodos() throws Exception {
        ArrayList<Endereco> lista = new ArrayList<>();
        try (Cursor c = listarTodosPorCursor()) {
            Endereco e;
            while (c.moveToNext()) {
                e = createInstance(c);
                lista.add(e);
            }
            return lista;
        }
    }

    @NonNull
    private Endereco createInstance(Cursor c) {
        Endereco e = Endereco.getInstance(c.getLong(c.getColumnIndex("_id")));

        e.setLogradouroTipo(c.getString(c.getColumnIndex("tp_logradouro")));
        e.setLogradouroNome(c.getString(c.getColumnIndex("nm_logradouro")));
        e.setNumero(c.getString(c.getColumnIndex("nr_endereco")));
        e.setComplemento(c.getString(c.getColumnIndex("ds_complemento")));
        e.setCep(c.getString(c.getColumnIndex("nr_cep")));
        e.setBairroNome(c.getString(c.getColumnIndex("nm_bairro")));
        e.setMunicipioNome(c.getString(c.getColumnIndex("nm_municipio")));
        e.setUf(UF.valueOf(c.getString(c.getColumnIndex("nm_uf"))));

        return e;
    }

    public Cursor listarTodosPorCursor() throws Exception {
        return db.query("endereco", null, null, null, null, null, null);
    }

    public void abrirDB() {
        db = getWritableDatabase();
    }

    public void fecharDB() {
        if (db != null) {
            db.close();
        }
    }
}

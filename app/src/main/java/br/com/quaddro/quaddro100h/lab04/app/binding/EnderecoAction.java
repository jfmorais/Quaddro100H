package br.com.quaddro.quaddro100h.lab04.app.binding;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.databinding.EnderecoAlterarViewBinding;
import br.com.quaddro.quaddro100h.lab04.app.service.ConsultarCEPService;

public class EnderecoAction {

    private EnderecoAlterarViewBinding binding;

    public EnderecoAction(EnderecoAlterarViewBinding binding) {
        this.binding = binding;
    }

    public void consultarCEP(View v) {
        Context c = v.getContext();

        Toast.makeText(c, R.string.lab04_endereco_consultar,
                Toast.LENGTH_SHORT).show();

        // No caso de IntentService o intent precisa ser explícito
        Intent i = new Intent(c, ConsultarCEPService.class);
        i.putExtra("cep", binding.cep.getText().toString());
        c.startService(i);
    }
}
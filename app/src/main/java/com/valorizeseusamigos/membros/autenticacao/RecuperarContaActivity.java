package com.valorizeseusamigos.membros.autenticacao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.valorizeseusamigos.membros.databinding.ActivityRecuperarContaBinding;
import com.valorizeseusamigos.membros.helper.FirebaseHelper;

public class RecuperarContaActivity extends AppCompatActivity {

    private ActivityRecuperarContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperarContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    public void validaDados(){

        String email = binding.edtEmail.getText().toString();

        if(!email.isEmpty()){

            ocultarTeclado();

            binding.progressBar.setVisibility(View.VISIBLE);

            recuperarConta(email);

        }else {
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Informe seu email.");
        }

    }

    private void recuperarConta(String email) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(
                email
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this, "Acabamos de te enviar um link via e-mail.", Toast.LENGTH_SHORT).show();
            }else {

                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    // Oculta o teclado do dispositivo
    private void ocultarTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.edtEmail.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }



    private void configClicks(){
        binding.btnRecuperarConta.setOnClickListener(v -> validaDados());
    }


}
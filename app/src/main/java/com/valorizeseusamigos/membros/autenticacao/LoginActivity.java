package com.valorizeseusamigos.membros.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.valorizeseusamigos.membros.app.MainActivity;
import com.valorizeseusamigos.membros.databinding.ActivityLoginBinding;
import com.valorizeseusamigos.membros.helper.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    private void validaDados() {
        String email = binding.edtEmail.getText().toString();
        String senha = binding.edtSenha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                ocultarTeclado();

                binding.progressBar.setVisibility(View.VISIBLE);

                logar(email, senha);

            } else {
                binding.edtSenha.requestFocus();
                binding.edtSenha.setError("Informe sua senha.");
            }
        } else {
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Informe seu email.");
        }

    }

    private void logar(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                finish();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Log.i("INFOTESTE", "logar: " + e.getMessage()));
    }

    public void criarConta() {
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void recuperarConta() {
        startActivity(new Intent(this, RecuperarContaActivity.class));
    }

    // Oculta o teclado do dispositivo
    private void ocultarTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.edtEmail.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void configClicks(){
        binding.btnLogin.setOnClickListener(v -> validaDados());
        binding.textCriarConta.setOnClickListener(v -> criarConta());
        binding.textRecuperarConta.setOnClickListener(v -> recuperarConta());
    }
}
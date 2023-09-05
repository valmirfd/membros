package com.valorizeseusamigos.membros.autenticacao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.valorizeseusamigos.membros.app.MainActivity;
import com.valorizeseusamigos.membros.databinding.ActivityCadastroBinding;
import com.valorizeseusamigos.membros.helper.FirebaseHelper;
import com.valorizeseusamigos.membros.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    private void validaDados(){
        String nome = binding.edtNome.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();
        String telefone = binding.edtTelefone.getText().toString().trim();
        String senha = binding.edtSenha.getText().toString().trim();
        String confirmaSenha = binding.edtConfirmaSenha.getText().toString().trim();

        if(!nome.isEmpty()){
            if(!email.isEmpty()){
                if(!telefone.isEmpty()){
                    if(!senha.isEmpty()){
                        if(!confirmaSenha.isEmpty()){
                            if(senha.equals(confirmaSenha)){

                                ocultarTeclado();

                                binding.progressBar.setVisibility(View.VISIBLE);

                                Usuario usuario = new Usuario();
                                usuario.setNome(nome);
                                usuario.setEmail(email);
                                usuario.setTelefone(telefone);
                                usuario.setSenha(senha);
                                usuario.setSaldo(0);

                                cadastrarUsuario(usuario);

                                Toast.makeText(this, "Tudo certo", Toast.LENGTH_SHORT).show();

                            }else{
                                binding.edtConfirmaSenha.requestFocus();
                                binding.edtConfirmaSenha.setError("Senha não confere");
                            }
                        }else{
                            binding.edtConfirmaSenha.requestFocus();
                            binding.edtConfirmaSenha.setError("Por favor confirme sua senha");
                        }
                    }else{
                        binding.edtSenha.requestFocus();
                        binding.edtSenha.setError("Por favor digite sua senha");
                    }
                }else{
                    binding.edtTelefone.requestFocus();
                    binding.edtTelefone.setError("Por favor digite seu telefone");
                }
            }else{
                binding.edtEmail.requestFocus();
                binding.edtEmail.setError("Por favor digite seu email");
            }
        }else{
            binding.edtNome.requestFocus();
            binding.edtNome.setError("Por favor digite seu nome");
        }

    }

    private void cadastrarUsuario(Usuario usuario) {

        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String id = task.getResult().getUser().getUid();
                usuario.setId(id);
                salvarDadosUsuario(usuario);

            }else{
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }

            binding.progressBar.setVisibility(View.GONE);
        });

    }

    private void salvarDadosUsuario(Usuario usuario) {

        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(usuario.getId());
        usuarioRef.setValue(usuario).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }else{
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Oculta o teclado do dispositivo
    private void ocultarTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.edtEmail.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void configClicks(){

        binding.btnCriarConta.setOnClickListener(v -> validaDados());
    }


}
package com.valorizeseusamigos.membros.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.valorizeseusamigos.membros.autenticacao.LoginActivity;
import com.valorizeseusamigos.membros.databinding.ActivityMainBinding;
import com.valorizeseusamigos.membros.deposito.DepositoFormActivity;
import com.valorizeseusamigos.membros.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    private void configClicks(){
        binding.cardDeposito.setOnClickListener(v -> {
            startActivity(new Intent(this, DepositoFormActivity.class));
        });

        binding.cardDeslogar.setOnClickListener(v -> {
            FirebaseHelper.getAuth().signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

}
package com.valorizeseusamigos.membros.deposito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.valorizeseusamigos.membros.R;
import com.valorizeseusamigos.membros.databinding.ActivityDepositoReciboBinding;
import com.valorizeseusamigos.membros.helper.FirebaseHelper;
import com.valorizeseusamigos.membros.helper.GetMask;
import com.valorizeseusamigos.membros.model.Deposito;

public class DepositoReciboActivity extends AppCompatActivity {

    private ActivityDepositoReciboBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDepositoReciboBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configToolbar();
        configClicks();
        getDeposito();



    }

    private void getDeposito(){
        String idDeposito = (String) getIntent().getSerializableExtra("idDeposito");

        DatabaseReference depositoRef = FirebaseHelper.getDatabaseReference()
                .child("depositos")
                .child(idDeposito);
        depositoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Deposito deposito = snapshot.getValue(Deposito.class);
                configDados(deposito);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configDados(Deposito deposito){
        binding.textCodigo.setText(deposito.getId());
        binding.textData.setText(GetMask.getDate(deposito.getData(), 3));
        binding.textValor.setText(getString(R.string.text_valor, GetMask.getValor(deposito.getValor())));
    }

    private void configClicks(){
        binding.btnOK.setOnClickListener(v -> finish());
    }

    private void configToolbar(){
        binding.include.textTitulo.setText("Recibo");
    }
}
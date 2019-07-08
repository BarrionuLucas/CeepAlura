package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.ceep.R;
import br.com.alura.ceep.Utils.Preferencias;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        int duracao = 500;
        if(Preferencias.primeiroAcesso(this)){
            duracao = 2000;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Preferencias.salvarAcesso(getApplicationContext());
                Intent main = new Intent(SplashScreenActivity.this, ListaNotasActivity.class);
                startActivity(main);
            }
        }, duracao);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import br.com.alura.ceep.R;

public class SplashScreenActivity extends AppCompatActivity {

    public static final String MODO_ACESSO = "MODO_ACESSO";
    public static final String PRIMEIRO_ACESSO = "PRIMEIRO_ACESSO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        int duracao = 500;
        if(primeiroAcesso()){
            duracao = 2000;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                salvarAcesso();
                Intent main = new Intent(SplashScreenActivity.this, ListaNotasActivity.class);
                startActivity(main);
            }
        }, duracao);

    }

    private boolean primeiroAcesso() {
        SharedPreferences prefs = getSharedPreferences(MODO_ACESSO, MODE_PRIVATE);
        return prefs.getBoolean(PRIMEIRO_ACESSO, true);
    }

    private void salvarAcesso() {
        SharedPreferences.Editor editor = getSharedPreferences(MODO_ACESSO, MODE_PRIVATE).edit();
        editor.putBoolean(PRIMEIRO_ACESSO, false);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaCoresAdapter;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnColorClickListener;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR_INSERE = "Insere nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera nota";
    private int posicaoRecibida = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descricao;
    private ConstraintLayout layoutNotaFormulario;
    private RecyclerView rvCores;
    private ListaCoresAdapter listaCoresAdapter;
    private String corDeFundoSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();

        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra(CHAVE_NOTA)){
            setTitle(TITULO_APPBAR_ALTERA);
            Nota notaRecebida = (Nota) dadosRecebidos
                    .getSerializableExtra(CHAVE_NOTA);
            posicaoRecibida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencheCampos(notaRecebida);
        }
    }

    private void preencheCampos(Nota notaRecebida) {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
        layoutNotaFormulario = findViewById(R.id.layout_nota_formulario);
        List<String> listaCores = pegaTodasCores();
        configuraRvCores(listaCores);
    }

    private List<String> pegaTodasCores() {
        List<String> cores = new ArrayList<>();

        cores.add("#FFFFFF");
        cores.add("#408EC9");
        cores.add("#EC2F4B");
        cores.add("#9ACD32");
        cores.add("#F9F256");
        cores.add("#F1CBFF");
        cores.add("#D2D4DC");
        cores.add("#A47C48");
        cores.add("#BE29EC");

        return cores;
    }

    private void configuraRvCores(List<String> listaCores) {
        rvCores = findViewById(R.id.lista_cores);
        configuraAdapter(rvCores, listaCores);
    }

    private void configuraAdapter(RecyclerView rvCores, List<String> listaCores) {
        listaCoresAdapter = new ListaCoresAdapter(listaCores, this);
        rvCores.setAdapter(listaCoresAdapter);
        listaCoresAdapter.setOnColorClickListener(new OnColorClickListener() {
            @Override
            public void onItemClick(String corEscolhida) {
                layoutNotaFormulario.setBackgroundColor(Color.parseColor(corEscolhida));
                corDeFundoSelecionada = corEscolhida;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(ehMenuSalvaNota(item)){
            Nota notaCriada = criaNota();
            retornaNota(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecibida);
        setResult(Activity.RESULT_OK,resultadoInsercao);
    }

    @NonNull
    private Nota criaNota() {
        return new Nota(titulo.getText().toString(),
                descricao.getText().toString(),
                corDeFundoSelecionada
        );
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }
}

package br.com.alura.ceep.ui.activity;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.database.NotasDatabase;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaCoresAdapter;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnColorClickListener;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity implements ControladorUIFormulario{


    public static final String TITULO_APPBAR_INSERE = "Insere nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera nota";
    public static final String ID_NOTA = "idNota";
    public static final String COR_DE_FUNDO = "bgColor";
    public static final String ERRO_CONSULTA_COR = "Não foi posssível recuperar a cor definida";
    private TextView titulo;
    private TextView descricao;
    private ConstraintLayout layoutNotaFormulario;
    private RecyclerView rvCores;
    private ListaCoresAdapter listaCoresAdapter;
    private String corDeFundoSelecionada;
    private NotaDAO notaDao;
    private NotasDatabase database;
    private ProgressBar loadingSalvaNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();
        notaDao = database.getNotaDAO();
        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra(ID_NOTA)){
            setTitle(TITULO_APPBAR_ALTERA);
            Long idNota = dadosRecebidos.getLongExtra(ID_NOTA, POSICAO_INVALIDA);
            Nota notaRecebida = notaDao.buscaNota(idNota);
            preencheCampos(notaRecebida);
        }else{
            Toast.makeText(this, "Houve uma falha na recuperação da nota", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            corDeFundoSelecionada = savedInstanceState.getString(COR_DE_FUNDO);
            try{
                layoutNotaFormulario.setBackgroundColor(Color.parseColor(corDeFundoSelecionada));
            }catch (Exception e ){
                e.printStackTrace();
                layoutNotaFormulario.setBackgroundColor(Color.parseColor(getResources().getString(R.color.brancoLista)));
                Toast.makeText(getApplicationContext(), ERRO_CONSULTA_COR, Toast.LENGTH_SHORT).show();
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("bgColor", corDeFundoSelecionada);
        super.onSaveInstanceState(outState);
    }

    private void preencheCampos(Nota notaRecebida) {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
        corDeFundoSelecionada = notaRecebida.getCor();
        layoutNotaFormulario.setBackgroundColor(Color.parseColor(corDeFundoSelecionada));
    }

    @SuppressLint("ResourceType")
    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
        layoutNotaFormulario = findViewById(R.id.layout_nota_formulario);
        loadingSalvaNota = findViewById(R.id.loading_salva_nota);
        corDeFundoSelecionada = getResources().getString(R.color.brancoLista);
        List<String> listaCores = pegaTodasCores();
        configuraRvCores(listaCores);
    }

    @SuppressLint("ResourceType")
    private List<String> pegaTodasCores() {
        List<String> cores = new ArrayList<>();
        cores.add(getResources().getString(R.color.brancoLista));
        cores.add(getResources().getString(R.color.azulLista));
        cores.add(getResources().getString(R.color.vermelhoLista));
        cores.add(getResources().getString(R.color.verdeLista));
        cores.add(getResources().getString(R.color.amareloLista));
        cores.add(getResources().getString(R.color.lilasLista));
        cores.add(getResources().getString(R.color.cinzaLista));
        cores.add(getResources().getString(R.color.marromLista));
        cores.add(getResources().getString(R.color.roxoLista));
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
                try {
                    layoutNotaFormulario.setBackgroundColor(Color.parseColor(corEscolhida));
                    corDeFundoSelecionada = corEscolhida;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Houve um erro ao selecionar a cor", Toast.LENGTH_SHORT).show();
                }
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
            salvaNota(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvaNota(Nota nota) {
        notaDao.altera(nota);
    }

    @NonNull
    private Nota criaNota() {
        return new Nota(titulo.getText().toString(),
                descricao.getText().toString(),
                corDeFundoSelecionada,
                notaDao.ultimaPosicao()
        );
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }

    @Override
    public void controlaLoading(int visibilidade) {
        loadingSalvaNota.setVisibility(visibilidade);
    }
}

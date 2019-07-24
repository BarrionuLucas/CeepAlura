package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class RemoveNotaPelaPosicaoTask extends AsyncTask<Void, Void, Void> {
    private final NotaDAO notaDao;
    private final ListaNotasAdapter adapter;
    private final int posicao;

    public RemoveNotaPelaPosicaoTask(NotaDAO notaDao, ListaNotasAdapter adapter, int posicao) {
        this.notaDao = notaDao;
        this.adapter = adapter;
        this.posicao = posicao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        notaDao.removePelaPosicao(posicao);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.remove(posicao);
    }
}

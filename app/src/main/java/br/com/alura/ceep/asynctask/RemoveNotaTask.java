package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class RemoveNotaTask extends AsyncTask<Void, Void, Void> {
    private final NotaDAO notaDao;
    private final ListaNotasAdapter adapter;
    private final Nota nota;

    public RemoveNotaTask(NotaDAO notaDao, ListaNotasAdapter adapter, Nota nota) {
        this.notaDao = notaDao;
        this.adapter = adapter;
        this.nota = nota;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        notaDao.remove(nota);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
//        adapter.remove(posicao);
    }
}
package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

public class ListaNotaTask extends AsyncTask<Void, Void, List<Nota>> {

    private final NotaDAO notaDao;
    ListaNotasListener listener;

    public ListaNotaTask(NotaDAO notaDao, ListaNotasListener listener) {
        this.notaDao = notaDao;
        this.listener = listener;
    }

    @Override
    protected List<Nota> doInBackground(Void... voids) {
        return notaDao.todos();
    }

    @Override
    protected void onPostExecute(List<Nota> notas) {
        super.onPostExecute(notas);

        listener.quandoEncontradas(notas);
    }

    public interface ListaNotasListener{
        void quandoEncontradas(List<Nota> notas);
    }
}

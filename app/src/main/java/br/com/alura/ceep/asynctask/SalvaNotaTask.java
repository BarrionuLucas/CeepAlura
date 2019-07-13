package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;
import android.view.View;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.activity.ControladorUIFormulario;

public class SalvaNotaTask extends AsyncTask<Void, Void, Void> {

    private final NotaDAO notaDao;
    private final Nota nota;
    private final ControladorUIFormulario listener;

    public SalvaNotaTask(NotaDAO notaDao, Nota nota, ControladorUIFormulario listener) {
        this.notaDao = notaDao;
        this.nota = nota;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.controlaLoading(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        notaDao.alteraNota(nota);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.controlaLoading(View.GONE);
    }

}

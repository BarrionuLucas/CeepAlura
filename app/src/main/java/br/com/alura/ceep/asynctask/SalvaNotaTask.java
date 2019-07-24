package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;
import android.view.View;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

public class SalvaNotaTask extends BaseNotaTask {

    private final NotaDAO notaDao;
    private final Nota nota;


    public SalvaNotaTask(NotaDAO notaDao, Nota nota, FinalizadaListener listener) {
        super(listener);
        this.notaDao = notaDao;
        this.nota = nota;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        nota.setPosicao(notaDao.ultimaPosicao());
        notaDao.alteraNota(nota);
        return null;
    }

}

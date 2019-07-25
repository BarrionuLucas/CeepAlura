package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;

public abstract class BaseNotaFinalizaTask extends AsyncTask<Void, Void, Void> {

    private final FinalizadaListener listener;

    public BaseNotaFinalizaTask(FinalizadaListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizada();
    }

    public interface FinalizadaListener {
        void quandoFinalizada();
    }
}

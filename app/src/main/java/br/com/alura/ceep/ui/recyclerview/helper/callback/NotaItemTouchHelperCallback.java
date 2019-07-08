package br.com.alura.ceep.ui.recyclerview.helper.callback;



import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ListaNotasAdapter adapter;
    private final NotaDAO dao;

    public NotaItemTouchHelperCallback(ListaNotasAdapter adapter, NotaDAO dao) {
        this.adapter = adapter;
        this.dao = dao;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int marcacoesDeArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP
                | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        trocaNotas(posicaoInicial, posicaoFinal);
        return true;
    }

    private void trocaNotas(int posicaoInicial, int posicaoFinal) {
        trataPosicaoNotaBanco(posicaoInicial, posicaoFinal);
        adapter.troca(posicaoInicial, posicaoFinal);
    }

    private void trataPosicaoNotaBanco(int posicaoInicial, int posicaoFinal) {
        Nota notaPosFinal = dao.buscaNotaPelaPosicao(posicaoFinal);
        Nota notaPosInicial = dao.buscaNotaPelaPosicao(posicaoInicial);
        notaPosFinal.setPosicao(posicaoInicial);
        notaPosInicial.setPosicao(posicaoFinal);
        dao.alteraNota(notaPosFinal);
        dao.alteraNota(notaPosInicial);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoDaNotaDeslizada = viewHolder.getAdapterPosition();
        removeNota(posicaoDaNotaDeslizada);
    }

    private void removeNota(int posicao) {
        dao.removePelaPosicao(posicao);
        adapter.remove(posicao);
    }
}

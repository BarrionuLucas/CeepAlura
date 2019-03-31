package br.com.alura.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnColorClickListener;

public class ListaCoresAdapter extends RecyclerView.Adapter<ListaCoresAdapter.CorViewHolder>{
    private final List<Integer> cores;
    private final Context context;
    private OnColorClickListener onColorClickListener;

    public ListaCoresAdapter(List<Integer> cores, Context context) {
        this.cores = cores;
        this.context = context;
    }

    public void setOnColorClickListener(OnColorClickListener onColorClickListener) {
        this.onColorClickListener = onColorClickListener;
    }

    @Override
    public CorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_cor, parent, false);
        return new CorViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(CorViewHolder holder, int position) {
        Integer cor = cores.get(position);
        holder.setCorDeFundo(cor);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CorViewHolder extends RecyclerView.ViewHolder {
        View corDeFundo;
        public CorViewHolder(View itemView) {
            super(itemView);
            corDeFundo = itemView.findViewById(R.id.opcao_item_cor);
        }

        public void setCorDeFundo(Integer cor) {
            corDeFundo.setBackgroundColor(cor);
        }
    }
}


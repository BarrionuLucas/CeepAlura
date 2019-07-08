package br.com.alura.ceep.dao;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.alura.ceep.model.Nota;

@Dao
public abstract class NotaDAO {

    @Insert
    public abstract void insere(Nota... notas);

    @Query("SELECT * FROM Nota " +
            "ORDER BY posicao ASC")
    public abstract List<Nota> todos();

    @Query("SELECT * FROM Nota " +
            "WHERE idNota = :idNota")
    public abstract Nota buscaNota(long idNota);

    @Query("SELECT * FROM Nota " +
            "WHERE posicao = :posicao")
    public abstract Nota buscaNotaPelaPosicao(int posicao);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void altera(List<Nota> todasNotas);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void alteraNota(Nota nota);

    @Delete
    public abstract void remove(Nota nota);

    @Query("DELETE FROM Nota")
    public abstract void removeTodos();

    @Query("DELETE FROM Nota where posicao = :posicao")
    public abstract void removePelaPosicao(int posicao);

    @Query("SELECT COUNT(*) FROM Nota")
    public abstract int ultimaPosicao();
}

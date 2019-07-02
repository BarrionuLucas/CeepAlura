package br.com.alura.ceep.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.alura.ceep.model.Nota;

@Dao
public interface NotaDAO {

    @Insert
    void insere(Nota... notas);

    @Query("SELECT * FROM Nota " +
            "ORDER BY posicao ASC")
    List<Nota> todos();

    @Query("SELECT * FROM Nota " +
            "WHERE idNota = :idNota")
    Nota buscaNota(long idNota);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void altera(Nota... nota);

    @Delete
    public void remove(Nota nota);

    @Query("DELETE FROM Nota")
    public void removeTodos();

    @Query("SELECT COUNT(*) FROM Nota")
    public int ultimaPosicao();
}

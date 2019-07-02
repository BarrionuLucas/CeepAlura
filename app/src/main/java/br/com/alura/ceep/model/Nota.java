package br.com.alura.ceep.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Nota implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private Long idNota;
    private final String titulo;
    private final String descricao;
    private final String cor;
    private final int posicao;


    public Nota(String titulo, String descricao, String cor, int posicao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.cor = cor;
        this.posicao = posicao;
    }

    public Long getIdNota() {
        return idNota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCor() {
        return cor;
    }

    public int getPosicao() {
        return posicao;
    }
}
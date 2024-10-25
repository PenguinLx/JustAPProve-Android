package br.ifsul.justapprove.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

public class Questao implements Parcelable {
    private Integer id;

    // talvez seja byte[]
    private Blob descricao;

    private List<Alternativa> alternativas;

    public Questao(){

    }

    protected Questao(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
    }

    public static final Creator<Questao> CREATOR = new Creator<Questao>() {
        @Override
        public Questao createFromParcel(Parcel in) {
            return new Questao(in);
        }

        @Override
        public Questao[] newArray(int size) {
            return new Questao[size];
        }
    };

    public Blob getDescricao() {
        return descricao;
    }

    public void setDescricao(Blob descricao) {
        this.descricao = descricao;
    }

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
    }

}

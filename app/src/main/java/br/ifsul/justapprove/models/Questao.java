package br.ifsul.justapprove.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

public class Questao {

    // talvez seja byte[]
    private String descricao;

    private List<Alternativa> alternativas;

//    public Questao(String descricao, List<Alternativa> alternativas){
//        this.descricao = descricao;
//        this.alternativas = alternativas;
//    }
//
//    protected Questao(Parcel in) {
//        descricao = in.readString();
//        alternativas = in.createTypedArrayList(Alternativa.CREATOR);
//    }
//
//    public static final Creator<Questao> CREATOR = new Creator<Questao>() {
//        @Override
//        public Questao createFromParcel(Parcel in) {
//            return new Questao(in);
//        }
//
//        @Override
//        public Questao[] newArray(int size) {
//            return new Questao[size];
//        }
//    };

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel dest, int flags) {
////        if (id == null) {
////            dest.writeByte((byte) 0);
////        } else {
////            dest.writeByte((byte) 1);
////            dest.writeInt(id);
////        }
//        dest.writeString(descricao);
//        dest.writeTypedList(alternativas);
//    }

}

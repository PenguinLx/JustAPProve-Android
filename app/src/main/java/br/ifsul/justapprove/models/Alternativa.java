package br.ifsul.justapprove.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Alternativa {

     private boolean correta;

     private String descricao;

//     private Alternativa(String descricao, boolean correta) {
//         this.descricao = descricao;
//         this.correta = correta;
//     }
//
//    protected Alternativa(Parcel in) {
//        correta = in.readByte() != 0;
//        descricao = in.readString();
//    }
//
//    public static final Creator<Alternativa> CREATOR = new Creator<Alternativa>() {
//        @Override
//        public Alternativa createFromParcel(Parcel in) {
//            return new Alternativa(in);
//        }
//
//        @Override
//        public Alternativa[] newArray(int size) {
//            return new Alternativa[size];
//        }
//    };

    public boolean isCorreta() {
        return correta;
    }

    public void setCorreta(boolean correta) {
        this.correta = correta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        dest.writeByte((byte) (correta ? 1 : 0));
//        dest.writeString(descricao);
//    }
}

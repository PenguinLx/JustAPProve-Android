package br.ifsul.justapprove.retrofit;

import java.util.List;

import br.ifsul.justapprove.models.ProvaAnterior;
import br.ifsul.justapprove.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProvaAnteriorApi {



    @GET("/prova/readAll")
    Call<List<ProvaAnterior>> getProvas();

    @GET("/prova/read/{id}")
    Call<ProvaAnterior> readProvaAnterior(@Path("id") Integer id);

    @POST("/prova/save")
    Call<ProvaAnterior> saveProva(@Body ProvaAnterior prova);

    @PUT("/prova/update{id}")
    Call<ProvaAnterior> updateProva(@Path("id") Integer id, @Body ProvaAnterior prova);

    @DELETE("/prova/delete{id}")
    Call<Usuario> deleteProva(@Path("id") Integer id);

}

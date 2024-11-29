package br.ifsul.justapprove.retrofit;

import java.util.List;

import br.ifsul.justapprove.models.Alternativa;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlternativaApi {

    @GET("/alternativa/readAll")
    Call<List<Alternativa>> getAllAlternativa();

    @GET("/alternativa/read/{id}")
    Call<Alternativa> readAlternativa(@Path("id") Integer id);

    @POST("/alternativa/save")
    Call<Alternativa> saveAlternativa(@Body Alternativa alternativa);

    @PUT("/alternativa/update/{id}")
    Call<Alternativa> updateAlternativa(@Path("id") Integer id, @Body Alternativa alternativa);

    @DELETE("/alternativa/delete/{id}")
    Call<Alternativa> deleteAlternativa(@Path("id") Integer id);
}

package br.ifsul.justapprove.retrofit;

import java.util.List;

import br.ifsul.justapprove.models.Simulado;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SimuladoApi {
    @GET("/simulado/{id}")
    Call<Simulado> getSimulado(@Path("id") int id);

    @GET("/simulado/readAll")
    Call<List<Simulado>> getAllSimulado();

    @POST("/simulado/save")
    Call<Simulado> saveSimulado(@Body Simulado simulado);

    @PUT("/simulado/update/{id}")
    Call<Simulado> updateSimulado(@Path("id") Integer id, @Body Simulado simulado);
    @DELETE("/simulado/delete{id}")
    Call<Simulado> deleteSimulado(@Path("id") Integer id);

}

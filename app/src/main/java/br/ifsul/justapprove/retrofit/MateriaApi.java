package br.ifsul.justapprove.retrofit;

import java.util.List;

import br.ifsul.justapprove.models.Materia;
import br.ifsul.justapprove.models.TipoMateria;
import br.ifsul.justapprove.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MateriaApi {

    @GET("/materia/readAll")
    Call<List<Materia>> getAllMateria();

    @GET("/materia/readAllByTipo/{tipo}")
    Call<List<Materia>> getAllMateriaByTipo(@Query("tipo")String tipo);
    @GET("/materia/read/{id}")
    Call<Materia> readMateria(@Path("id") Integer id);

    @POST("/materia/save")
    Call<Materia> saveMateria(@Body Materia materia);

    @PUT("/materia/update/{id}")
    Call<Materia> updateMateria(@Path("id") Integer id, @Body Materia materia);

    @DELETE("/materia/delete/{id}")
    Call<Materia> deleteMateria(@Path("id") Integer id);
}

package br.ifsul.justapprove.retrofit;

import java.util.List;

import br.ifsul.justapprove.models.Material;
import br.ifsul.justapprove.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MaterialApi {

    @GET("/material/readAll")
    Call<List<Material>> getAllMaterial();

    @GET("/material/read/{id}")
    Call<Material> readMaterial(@Path("id") Integer id);

    @POST("/material/save")
    Call<Material> saveMaterial(@Body Material material);

    @PUT("/material/update/{id}")
    Call<Material> updateMaterial(@Path("id") Integer id, Material material);

    @DELETE("/usuarios/delete{id}")
    Call<Usuario> deleteUsuario(@Path("id") Integer id);
}

package br.ifsul.justapprove.retrofit;

import java.util.List;

import br.ifsul.justapprove.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UsuarioApi {

    // isso pode estar muito errado
    @GET("/usuario/get-all?sort=desc")
    Call<List<Usuario>> getAllUsuario();

    @POST("/usuario/save")
    Call<Usuario> saveUsuario(@Body Usuario usuario);
}

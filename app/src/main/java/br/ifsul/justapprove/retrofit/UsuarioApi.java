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

public interface UsuarioApi {


    @GET("/usuario/readAll")
    Call<List<Usuario>> getAllUsuario();

    @GET("/usuario/readAllUsuariosByPontos")
    Call<List<Usuario>> getAllUsuarioByPontos();

    @GET("/usuario/readUserById/{id}")
    Call<Usuario> readUsuario(@Path("id") Integer id);

    @POST("/usuario/saveUser")
    Call<Usuario> saveUsuario(@Body Usuario usuario);

    @PUT("/usuario/updateUsuario/{id}")
    Call<Usuario> updateUsuario(@Path("id") Integer id, @Body Usuario usr);

    @PUT("/usuario/updatePonto/{id}")
    Call<Usuario> updateUsuarioPontos(@Path("id") Integer id, @Body Usuario usr);

    @DELETE("/usuario/delete{id}")
    Call<Usuario> deleteUsuario(@Path("id") Integer id);

    @POST("/usuario/login")
    Call<LoginResponse> loginUsuario(@Body LoginRequest loginRequest);
}

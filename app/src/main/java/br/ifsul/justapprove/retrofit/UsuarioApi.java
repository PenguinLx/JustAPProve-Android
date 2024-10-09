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

    // Ainda pode estar errado(tem que testar), mas está mais completo
    // botar a primeira / como RequestMapping do spring e segunda / como método utilizado
    // exemplo /usuarios/saveUser, /simulado/save
    @GET("/usuarios/readAll")
    Call<List<Usuario>> getAllUsuario();

    @GET("/usuarios/readAllUsuariosByPontos")
    Call<List<Usuario>> getAllUsuarioByPontos();

    @GET("/usuarios/readUserById/{id}")
    Call<Usuario> readUsuario(@Path("id") Integer id);

    @POST("/usuarios/saveUser")
    Call<Usuario> saveUsuario(@Body Usuario usuario);

    @PUT("/usuarios/update/{id}")
    Call<Usuario> updateUsuario(@Path("id") Integer id, @Body Usuario usr);

    @DELETE("/usuarios/delete{id}")
    Call<Usuario> deleteUsuario(@Path("id") Integer id);

    @POST("/usuarios/login")
    Call<LoginResponse> loginUsuario(@Body LoginRequest loginRequest);


}

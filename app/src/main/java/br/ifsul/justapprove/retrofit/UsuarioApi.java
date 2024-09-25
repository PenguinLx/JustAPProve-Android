package br.ifsul.justapprove.retrofit;

import br.ifsul.justapprove.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioApi {


    @POST("/usuario/save")
    Call<Usuario> saveUsuario(@Body Usuario usuario);
}

package br.ifsul.justapprove.retrofit;

import java.util.List;

import br.ifsul.justapprove.models.Questao;
import br.ifsul.justapprove.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuestaoApi {

    @GET("/questao/readAll")
    Call<List<Questao>> getAllQuestao();


    @GET("/questao/read/{id}")
    Call<Questao> readQuestao(@Path("id") Integer id);
    @POST("/questao/save")
    Call<Questao> saveQuestao(@Body Questao questao);

    @GET("/questao/gerarSimulado")
    Call<List<Questao>> gerarSimulado(@Query("numero") int numero);

    @PUT("/questao/update{id}")
    Call<Usuario> updateQuestao(@Path("id") Integer id, @Body Usuario usr);

    @DELETE("/questao/delete{id}")
    Call<Usuario> deleteQuestao(@Path("id") Integer id);

}

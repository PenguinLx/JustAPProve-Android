package br.ifsul.justapprove.retrofit;

import br.ifsul.justapprove.models.Questao;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SimuladoGeradoApi {
    @GET("/questao/{questao_id}")
    Call<Questao> getQuestao(@Path("questao_id") int questao_id);
}

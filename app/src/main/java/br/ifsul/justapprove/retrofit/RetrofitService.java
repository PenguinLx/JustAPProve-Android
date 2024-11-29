package br.ifsul.justapprove.retrofit;

import com.google.gson.Gson;
import java.net.*;
import java.util.Enumeration;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit rfs;

    public RetrofitService()  {
        initializeRetrofit();

    }
    private void initializeRetrofit() {
        rfs = new Retrofit.Builder().baseUrl("http://192.168.1.8:8080").addConverterFactory(GsonConverterFactory.create(new Gson())).build(); /*pode ser a porta 9000 */
    }

    public Retrofit getRfs() {
        return rfs;
    }

}

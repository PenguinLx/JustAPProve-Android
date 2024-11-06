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
//    InetAddress IP;
//
//    {
//        try {
//            IP = InetAddress.getLocalHost();
//        } catch (UnknownHostException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public String ipAddress(){
//        try {
//            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//            while (networkInterfaces.hasMoreElements()) {
//                NetworkInterface networkInterface = networkInterfaces.nextElement();
//                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
//                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
//                while (addresses.hasMoreElements()) {
//                    InetAddress address = addresses.nextElement();
//                    if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
//
//                            return "http://" + address.getHostAddress() + ":8080";
//                        }
//                         //return "http://192.168.1.3:8080";
//                    }
//                }
//            }
//        } catch (SocketException ex) {
//            ex.printStackTrace();
//        }
//
//return  null;
//    }
    public RetrofitService()  {
        initializeRetrofit();

    }
    private void initializeRetrofit() {
        rfs = new Retrofit.Builder().baseUrl("http://192.168.134.102:8080").addConverterFactory(GsonConverterFactory.create(new Gson())).build(); /*pode ser a porta 9000 */
    }

    public Retrofit getRfs() {
        return rfs;
    }

}

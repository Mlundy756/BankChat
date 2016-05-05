package utsa.edu.BankApplication.API;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Matthew on 4/18/2016.
 */
public class Client {

    public static MessageService service;

    public static MessageService getClient() {
        if (service == null) {

            OkHttpClient okClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws IOException {
                                    return chain.proceed(chain.request());
                                }
                            })
                    .build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl("http://10.245.121.48/")
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = client.create(MessageService.class);
        }

        return service;
    }
}

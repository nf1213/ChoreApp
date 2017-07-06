package com.github.nf1213.choreapp;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nicole Felch on 6/7/17.
 */
@Module
public class ChoreModule {

    private ChoreApplication application;

    public ChoreModule(ChoreApplication choreApplication) {
        this.application = choreApplication;
    }

    @Provides
    public Context applicationContext() {
        return application;
    }

    @Provides
    @Singleton
    public ChoreRepository providesEventRepository(ChoreDatabase choreDatabase) {
        return new ChoreRepositoryImpl(choreDatabase);
    }

    @Provides
    @Singleton
    public ChoreDatabase providesEventDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), ChoreDatabase.class, "chore_db").build();
    }

    @Provides
    @Singleton
    public RestServiceInterface providesRestService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RestServiceInterface.class);
    }

}

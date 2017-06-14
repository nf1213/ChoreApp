package com.github.nf1213.choreapp;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    Context applicationContext() {
        return application;
    }

    @Provides
    @Singleton
    ChoreRepository providesEventRepository(ChoreDatabase choreDatabase) {
        return new ChoreRepositoryImpl(choreDatabase);
    }

    @Provides
    @Singleton
    ChoreDatabase providesEventDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), ChoreDatabase.class, "chore_db").build();
    }
}

package com.github.nf1213.choreapp.datastorage;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Nicole Felch on 6/7/17.
 */
@Dao
public interface ChoreDao {

    @Insert(onConflict = REPLACE)
    void insertChore(Chore chore);

    @Query("SELECT * FROM chore")
    LiveData<List<Chore>> getChores();

    @Query("SELECT * FROM chore WHERE id = :id LIMIT 1")
    LiveData<Chore> getChore(int id);

    @Delete
    void deleteChore(Chore chore);

    @Update(onConflict = REPLACE)
    void updateChore(Chore event);
}

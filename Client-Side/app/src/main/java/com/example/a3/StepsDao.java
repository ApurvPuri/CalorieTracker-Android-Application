package com.example.a3;

import android.arch.persistence.room.*;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepsDao {
    @Query("SELECT * FROM steps")
    List<Steps> getAll();


    @Query("SELECT * FROM steps WHERE sid = :reportId LIMIT 1")
   Steps findByID(int reportId);

    @Insert
    void insertAll(Steps... steps);

    @Insert
    long insert(Steps steps);

    @Delete
    void delete(Steps steps);

    @Update(onConflict = REPLACE)
    public void updateUsers(Steps... steps);

    @Query("DELETE FROM steps")
    void deleteAll();
}

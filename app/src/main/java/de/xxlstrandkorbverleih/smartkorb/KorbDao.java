package de.xxlstrandkorbverleih.smartkorb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface KorbDao {

    @Insert
    void insert(Korb korb);

    @Update
    void update(Korb korb);

    @Delete
    void delete(Korb korb);

    @Query("DELETE FROM korb_table")
    void deleteAllKörbe();

    @Query("SELECT * FROM korb_table ORDER BY number DESC")
    LiveData<List<Korb>> getAllKörbe();
}

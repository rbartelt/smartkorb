package de.xxlstrandkorbverleih.smartkorb.feature_korb.data.data_source;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

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

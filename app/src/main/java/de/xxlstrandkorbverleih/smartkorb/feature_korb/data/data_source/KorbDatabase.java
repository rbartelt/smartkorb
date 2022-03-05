package de.xxlstrandkorbverleih.smartkorb.feature_korb.data.data_source;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

@Database(entities = {Korb.class}, version = 3)
public abstract class KorbDatabase extends RoomDatabase {
    public abstract  KorbDao korbDao();
}

package hu.ait.rickstevesitinerary.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import hu.ait.rickstevesitinerary.data.ItineraryDatabase
import hu.ait.rickstevesitinerary.data.ItineraryDAO

@InstallIn(SingletonComponent::class)
@Module
class TodoDataModule {
    @Provides
    fun provideTodoDao(appDatabase: ItineraryDatabase): ItineraryDAO {
        return appDatabase.itineraryDao()
    }

    @Provides
    @Singleton
    fun provideTodoAppDatabase(
        @ApplicationContext appContext: Context
    ): ItineraryDatabase {
        return ItineraryDatabase.getDatabase(appContext)
    }
}
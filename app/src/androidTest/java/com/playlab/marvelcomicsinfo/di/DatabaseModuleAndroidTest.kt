package com.playlab.marvelcomicsinfo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.playlab.marvelcomicsinfo.data.local.MarvelDatabase
import com.playlab.marvelcomicsinfo.data.local.MarvelDatabaseAndroidTest
import com.playlab.marvelcomicsinfo.data.preferences.PreferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModuleAndroidTest {

    @Named("test_paging_db")
    @Provides
    @Singleton
    fun providePagingDatabase(
        @ApplicationContext context: Context
    ): MarvelDatabase =
        Room.inMemoryDatabaseBuilder(
            context,
            MarvelDatabase::class.java
        ).allowMainThreadQueries().build()

    @Named("test_db")
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MarvelDatabaseAndroidTest =
        Room.inMemoryDatabaseBuilder(
            context,
            MarvelDatabaseAndroidTest::class.java
        ).allowMainThreadQueries().build()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Named("test_ds")
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = TestScope(),
            produceFile = {
                appContext.preferencesDataStoreFile("test_settings")
            }
        )

    @Named("test_pds")
    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @Named("test_ds") dataStore: DataStore<Preferences>
    ): PreferencesDataStore = PreferencesDataStore(dataStore)

}
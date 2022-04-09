package com.appdate.marvelcomicsinfo.di

import android.content.Context
import androidx.room.Room
import com.appdate.marvelcomicsinfo.data.local.MarvelDatabase
import com.appdate.marvelcomicsinfo.util.Constants.MARVEL_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MarvelDatabase {
        return Room.databaseBuilder(
            context,
            MarvelDatabase::class.java,
            MARVEL_DATABASE
        ).build()
    }

}
package com.mynormeza.sampletodo.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.mynormeza.sampletodo.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule{

    @Provides
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides @Singleton
    fun providesAppDatabase(app: Application): AppDatabase = Room
        .databaseBuilder(app, AppDatabase::class.java, "todo.db")
        .fallbackToDestructiveMigration()
        .build()

}
package com.sirketismi.noteapp.di

import com.sirketismi.noteapp.util.MySharedPreferences
import com.sirketismi.noteapp.util.MySharedPreferencesInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class PreferencesModule {

    /*@BindsInstance
    @Provides
    fun provideMySharedPrefences(@ApplicationContext context: Context) : MySharedPreferences {
        return MySharedPreferences(context)
    }*/

    @Binds
    @Singleton
    abstract fun bindPreferences(pref : MySharedPreferences) : MySharedPreferencesInterface
}

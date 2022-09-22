package com.example.opgg_test.module

import com.example.opgg_test.data.repository.impl.SummonerRepository
import com.example.opgg_test.data.repository.impl.SummonerRepositoryImpl
import com.example.opgg_test.data.service.SummonerService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(ViewModelComponent::class)
abstract class ApiModule {
    @Binds
    @ViewModelScoped
    abstract fun bindSummonerRepository(summonerRepositoryImpl: SummonerRepositoryImpl): SummonerRepository

    companion object {
        private const val SUMMONER_BASE_URL = "https://codingtest.op.gg/"

        @Provides
        @ViewModelScoped
        fun provideSummonerService(): SummonerService =
            Retrofit.Builder()
                .baseUrl(SUMMONER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SummonerService::class.java)
    }
}
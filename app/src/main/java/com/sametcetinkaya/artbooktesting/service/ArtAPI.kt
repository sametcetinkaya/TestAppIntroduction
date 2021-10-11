package com.sametcetinkaya.artbooktesting.service

import com.sametcetinkaya.artbooktesting.model.ImageResponse
import com.sametcetinkaya.artbooktesting.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtAPI {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery : String,
        @Query("key") apiKey : String = API_KEY
    ): Response<ImageResponse>
}
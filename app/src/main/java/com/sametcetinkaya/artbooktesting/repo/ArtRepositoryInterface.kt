package com.sametcetinkaya.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.sametcetinkaya.artbooktesting.model.Art
import com.sametcetinkaya.artbooktesting.model.ImageResponse
import com.sametcetinkaya.artbooktesting.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>
}
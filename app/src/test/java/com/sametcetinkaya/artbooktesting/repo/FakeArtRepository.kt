package com.sametcetinkaya.artbooktesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sametcetinkaya.artbooktesting.model.Art
import com.sametcetinkaya.artbooktesting.model.ImageResponse
import com.sametcetinkaya.artbooktesting.util.Resource

class FakeArtRepository : ArtRepositoryInterface{
    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }
}
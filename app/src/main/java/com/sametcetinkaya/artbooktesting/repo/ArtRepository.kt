package com.sametcetinkaya.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.sametcetinkaya.artbooktesting.model.Art
import com.sametcetinkaya.artbooktesting.model.ImageResponse
import com.sametcetinkaya.artbooktesting.roomdb.ArtDao
import com.sametcetinkaya.artbooktesting.service.ArtAPI
import com.sametcetinkaya.artbooktesting.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val artAPI: ArtAPI
) : ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = artAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?:Resource.error("Error", null)
            }else {
                Resource.error("Error", null)
            }
        }catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }
}
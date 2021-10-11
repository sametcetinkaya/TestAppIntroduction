package com.sametcetinkaya.artbooktesting.model

data class ImageResponse(
    val hits : List<ImageResult>,
    val total : Int,
    val totalHilts : Int
)

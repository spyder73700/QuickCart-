package com.example.shopping.domain.useCase

import com.example.shopping.common.ResultState
import com.example.shopping.domain.models.ProductDataModels
import com.example.shopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToFavUseCase @Inject constructor(private val repo: Repo) {
    fun addtoFav(productDataModels: ProductDataModels): Flow<ResultState<String>> {
        return repo.addtoFav(productDataModels)

    }
}
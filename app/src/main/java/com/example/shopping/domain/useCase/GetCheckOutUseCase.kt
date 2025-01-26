package com.example.shopping.domain.useCase

import com.example.shopping.common.ResultState
import com.example.shopping.domain.models.ProductDataModels
import com.example.shopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCheckOutUseCase @Inject constructor(private val repo: Repo)  {

    fun getCheckoutUseCse(productId: String) : Flow<ResultState<ProductDataModels>> {
        return repo.getCheckout(productId)

    }
}
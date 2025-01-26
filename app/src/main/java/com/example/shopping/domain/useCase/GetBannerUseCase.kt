package com.example.shopping.domain.useCase

import com.example.shopping.common.ResultState
import com.example.shopping.domain.models.BannerDataModels
import com.example.shopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(private val repo: Repo) {
    fun getBannerUseCase() : Flow<ResultState<List<BannerDataModels>>> {
        return repo.getBanner()

    }
}
package com.example.shopping.domain.useCase

import com.example.shopping.common.ResultState
import com.example.shopping.domain.models.CategoryDataModels
import com.example.shopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(private val repo: Repo) {
    fun getAllCategoriesUseCase() : Flow<ResultState<List<CategoryDataModels>>> {
        return repo.getAllCategories()

    }
}
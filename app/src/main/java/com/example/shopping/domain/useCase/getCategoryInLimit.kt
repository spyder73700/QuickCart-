package com.example.shopping.domain.useCase

import com.example.shopping.common.ResultState
import com.example.shopping.domain.models.CategoryDataModels
import com.example.shopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getCategoryInLimit @Inject constructor(private val repo: Repo) {
    fun getCategoriesInLimited() : Flow<ResultState<List<CategoryDataModels>>> {
    return repo.getCategoriesInLimited()

}
}
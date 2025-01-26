package com.example.shopping.domain.useCase

import com.example.shopping.common.ResultState
import com.example.shopping.domain.models.UserDataParent
import com.example.shopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpDateUserDataUseCase @Inject constructor(private val repo: Repo)  {
    fun upDateUserData(userDataParent: UserDataParent) : Flow<ResultState<String>> {
        return repo.upDateUserData(userDataParent)

    }

}
package com.malibin.study.trying.mvvm.data.remote.source

import com.malibin.study.trying.mvvm.data.remote.mapper.RemoteDiariesMapper
import com.malibin.study.trying.mvvm.data.remote.service.MalibinService
import com.malibin.study.trying.mvvm.data.source.DiariesSource
import com.malibin.study.trying.mvvm.domain.Diary
import retrofit2.Response
import javax.inject.Inject

class DiariesRemoteSource @Inject constructor(
    private val malibinService: MalibinService,
    private val remoteDiariesMapper: RemoteDiariesMapper,
) : DiariesSource {
    override suspend fun getAllDiaries(): Result<List<Diary>> {
        val response = malibinService.getDiaries()
        if (response.isSuccessful) {
            return response.body().orEmpty()
                .map { remoteDiariesMapper.toDiary(it) }
                .let { Result.success(it) }
        }
        return Result.failure(IllegalStateException(response.message()))
    }

    override suspend fun getDiary(id: String): Result<Diary> {
        val response = malibinService.getDiary(id)
        if (response.isSuccessful) {
            val remoteDiary = response.body()
                ?: return Result.failure(IllegalStateException("response success but body is null!"))
            return Result.success(remoteDiariesMapper.toDiary(remoteDiary))
        }
        return Result.failure(IllegalStateException(response.message()))
    }

    override suspend fun saveDiary(diary: Diary): Result<Unit> {
        return malibinService.saveDiary(params = remoteDiariesMapper.toSaveDiaryParams(diary))
            .toResult()
    }

    override suspend fun updateDiary(diary: Diary): Result<Unit> {
        return malibinService.updateDiary(remoteDiariesMapper.toUpdateDiaryParams(diary)).toResult()
    }

    override suspend fun deleteDiary(diaryId: String): Result<Unit> {
        return malibinService.deleteDiary(diaryId).toResult()
    }

    private fun Response<Unit>.toResult(): Result<Unit> {
        return if (this.isSuccessful) Result.success(Unit)
        else Result.failure(IllegalStateException(this.message()))
    }
}

package com.malibin.study.trying.mvvm.data.remote.source

import com.malibin.study.trying.mvvm.data.remote.params.SaveDiaryParams
import com.malibin.study.trying.mvvm.data.remote.params.UpdateDiaryParams
import com.malibin.study.trying.mvvm.data.remote.service.MalibinService
import com.malibin.study.trying.mvvm.data.source.DiariesSource
import com.malibin.study.trying.mvvm.domain.Diary
import retrofit2.Response
import java.util.*

class DiariesRemoteSource(
    private val malibinService: MalibinService,
) : DiariesSource {
    override suspend fun getAllDiaries(): Result<List<Diary>> {
        val response = malibinService.getDiaries()
        if (response.isSuccessful) {
            val remoteDiaries = response.body().orEmpty()
            val diaries =
                remoteDiaries.map { Diary(it.id, it.title, it.content, Date(it.createdAt)) }
            return Result.success(diaries)
        }
        return Result.failure(IllegalStateException(response.message()))
    }

    override suspend fun getDiary(id: String): Result<Diary> {
        val response = malibinService.getDiary(id)
        if (response.isSuccessful) {
            val remoteDiary = response.body()
                ?: return Result.failure(IllegalStateException("response success but body is null!"))
            val diary = Diary(
                remoteDiary.id,
                remoteDiary.title,
                remoteDiary.content,
                Date(remoteDiary.createdAt),
            )
            return Result.success(diary)
        }
        return Result.failure(IllegalStateException(response.message()))
    }

    override suspend fun saveDiary(diary: Diary): Result<Unit> {
        return malibinService.saveDiary(
            params = SaveDiaryParams(
                title = diary.title,
                content = diary.content,
                createdAt = diary.createDate.time,
                id = diary.id,
            )
        ).toResult()
    }

    override suspend fun updateDiary(diary: Diary): Result<Unit> {
        return malibinService.updateDiary(
            params = UpdateDiaryParams(
                title = diary.title,
                content = diary.content,
                id = diary.id,
            )
        ).toResult()
    }

    override suspend fun deleteDiary(diaryId: String): Result<Unit> {
        return malibinService.deleteDiary(diaryId).toResult()
    }

    private fun Response<Unit>.toResult(): Result<Unit> {
        return if (this.isSuccessful) Result.success(Unit)
        else Result.failure(IllegalStateException(this.message()))
    }
}

package com.malibin.study.trying.mvvm.data.repository

import com.malibin.study.trying.mvvm.data.remote.params.SaveDiaryParams
import com.malibin.study.trying.mvvm.data.remote.params.UpdateDiaryParams
import com.malibin.study.trying.mvvm.data.remote.service.MalibinService
import com.malibin.study.trying.mvvm.data.source.DiariesSource
import com.malibin.study.trying.mvvm.domain.Diary
import com.malibin.study.trying.mvvm.domain.repository.DiariesRepository
import java.util.*

class RealDiariesRepository(
    private val diariesLocalSource: DiariesSource,
    private val malibinService: MalibinService,
) : DiariesRepository {

    override suspend fun getAllDiaries(): Result<List<Diary>> {
        val localDiaries = diariesLocalSource.getAllDiaries().getOrElse { emptyList() }
        if (localDiaries.isNotEmpty()) {
            return Result.success(localDiaries)
        }
        val response = malibinService.getDiaries()
        if (response.isSuccessful) {
            val remoteDiaries = response.body().orEmpty()
            remoteDiaries.forEach {
                diariesLocalSource.saveDiary(Diary(it.id, it.title, it.content, Date(it.createdAt)))
            }
            val diaries =
                remoteDiaries.map { Diary(it.id, it.title, it.content, Date(it.createdAt)) }
            return Result.success(diaries)
        }
        return Result.failure(IllegalStateException(response.message()))
    }

    override suspend fun getDiary(id: String): Result<Diary?> {
        val localDiary = diariesLocalSource.getDiary(id).getOrNull()
        if (localDiary != null) {
            return Result.success(localDiary)
        }
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
            diariesLocalSource.saveDiary(diary)
            return Result.success(diary)
        }
        return Result.failure(IllegalStateException(response.message()))
    }

    override suspend fun saveDiary(diary: Diary): Result<Unit> {
        return runCatching {
            malibinService.saveDiary(
                params = SaveDiaryParams(
                    title = diary.title,
                    content = diary.content,
                    createdAt = diary.createDate.time,
                    id = diary.id,
                )
            )
            diariesLocalSource.saveDiary(diary).getOrThrow()
        }
    }

    override suspend fun updateDiary(diary: Diary): Result<Unit> {
        return runCatching {
            malibinService.updateDiary(
                params = UpdateDiaryParams(
                    title = diary.title,
                    content = diary.content,
                    id = diary.id,
                )
            )
            diariesLocalSource.updateDiary(diary).getOrThrow()
        }
    }

    override suspend fun deleteDiary(diaryId: String): Result<Unit> {
        return runCatching {
            malibinService.deleteDiary(diaryId)
            diariesLocalSource.deleteDiary(diaryId).getOrThrow()
        }
    }
}

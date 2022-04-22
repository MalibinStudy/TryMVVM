package com.malibin.study.trying.mvvm.data.repository

import com.malibin.study.trying.mvvm.data.source.DiariesSource
import com.malibin.study.trying.mvvm.domain.Diary
import com.malibin.study.trying.mvvm.domain.repository.DiariesRepository

class RealDiariesRepository(
    private val diariesLocalSource: DiariesSource,
    private val diariesRemoteSource: DiariesSource,
) : DiariesRepository {

    override suspend fun getAllDiaries(): Result<List<Diary>> {
        val localDiaries = diariesLocalSource.getAllDiaries().getOrElse { emptyList() }
        if (localDiaries.isNotEmpty()) {
            return Result.success(localDiaries)
        }
        return diariesRemoteSource.getAllDiaries()
    }

    override suspend fun getDiary(id: String): Result<Diary> {
        return diariesLocalSource.getDiary(id).takeIf { it.isSuccess }
            ?: diariesRemoteSource.getDiary(id)
    }

    override suspend fun saveDiary(diary: Diary): Result<Unit> {
        return runCatching {
            diariesRemoteSource.saveDiary(diary).getOrThrow()
            diariesLocalSource.saveDiary(diary).getOrThrow()
        }
    }

    override suspend fun updateDiary(diary: Diary): Result<Unit> {
        return runCatching {
            diariesRemoteSource.updateDiary(diary).getOrThrow()
            diariesLocalSource.updateDiary(diary).getOrThrow()
        }
    }

    override suspend fun deleteDiary(diaryId: String): Result<Unit> {
        return runCatching {
            diariesRemoteSource.deleteDiary(diaryId).getOrThrow()
            diariesLocalSource.deleteDiary(diaryId).getOrThrow()
        }
    }
}

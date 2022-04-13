package com.malibin.study.trying.mvvm.data.repository

import com.malibin.study.trying.mvvm.data.local.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.local.entity.DiaryEntity
import com.malibin.study.trying.mvvm.data.remote.params.SaveDiaryParams
import com.malibin.study.trying.mvvm.data.remote.params.UpdateDiaryParams
import com.malibin.study.trying.mvvm.data.remote.service.MalibinService
import com.malibin.study.trying.mvvm.domain.Diary
import com.malibin.study.trying.mvvm.domain.repository.DiariesRepository
import java.util.*

class RealDiariesRepository(
    private val diariesDao: DiariesDao,
    private val malibinService: MalibinService,
) : DiariesRepository {

    override suspend fun getAllDiaries(): Result<List<Diary>> {
        val localDiaries = diariesDao.getAllDiaries()
        if (localDiaries.isNotEmpty()) {
            val diaries = localDiaries.map { Diary(it.id, it.title, it.content, it.createDate) }
            return Result.success(diaries)
        }
        val response = malibinService.getDiaries()
        if (response.isSuccessful) {
            val remoteDiaries = response.body().orEmpty()
            remoteDiaries.forEach {
                diariesDao.insertDiary(DiaryEntity(it.title, it.content, Date(it.createdAt), it.id))
            }
            val diaries =
                remoteDiaries.map { Diary(it.id, it.title, it.content, Date(it.createdAt)) }
            return Result.success(diaries)
        }
        return Result.failure(IllegalStateException(response.message()))
    }

    override suspend fun getDiary(id: String): Result<Diary> {
        val localDiary = diariesDao.getDiary(id)
        if (localDiary != null) {
            return Result.success(
                Diary(
                    localDiary.id,
                    localDiary.title,
                    localDiary.content,
                    localDiary.createDate
                )
            )
        }
        val response = malibinService.getDiary(id)
        if (response.isSuccessful) {
            val remoteDiary = response.body()
                ?: return Result.failure(IllegalStateException("response success but body is null!"))
            diariesDao.insertDiary(
                DiaryEntity(
                    remoteDiary.title,
                    remoteDiary.content,
                    Date(remoteDiary.createdAt),
                    remoteDiary.id,
                )
            )
            return Result.success(
                Diary(
                    remoteDiary.id,
                    remoteDiary.title,
                    remoteDiary.content,
                    Date(remoteDiary.createdAt),
                )
            )
        }
        return Result.failure(IllegalStateException(response.message()))
    }

    override suspend fun saveDiary(diary: Diary): Result<Unit> {
        return runCatching {
            diariesDao.insertDiary(
                DiaryEntity(
                    diary.title,
                    diary.content,
                    diary.createDate,
                    diary.id,
                )
            )
            malibinService.saveDiary(
                params = SaveDiaryParams(
                    title = diary.title,
                    content = diary.content,
                    createdAt = diary.createDate.time,
                    id = diary.id,
                )
            )
        }
    }

    override suspend fun updateDiary(diary: Diary): Result<Unit> {
        return runCatching {
            diariesDao.updateDiary(
                DiaryEntity(
                    diary.title,
                    diary.content,
                    diary.createDate,
                    diary.id,
                )
            )
            malibinService.updateDiary(
                params = UpdateDiaryParams(
                    title = diary.title,
                    content = diary.content,
                    id = diary.id,
                )
            )
        }
    }

    override suspend fun deleteDiary(diary: Diary): Result<Unit> {
        return runCatching {
            diariesDao.deleteDiary(diary.id)
            malibinService.deleteDiary(diary.id)
        }
    }
}

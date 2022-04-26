package com.malibin.study.trying.mvvm.data.local.source

import com.malibin.study.trying.mvvm.data.local.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.local.mapper.LocalDiariesMapper
import com.malibin.study.trying.mvvm.data.source.DiariesSource
import com.malibin.study.trying.mvvm.domain.Diary
import javax.inject.Inject

class DiariesLocalSource @Inject constructor(
    private val diariesDao: DiariesDao,
    private val localDiariesMapper: LocalDiariesMapper,
) : DiariesSource {
    override suspend fun getAllDiaries(): Result<List<Diary>> {
        return runCatching { diariesDao.getAllDiaries() }
            .map { diariesEntities -> diariesEntities.map { localDiariesMapper.toDiary(it) } }
    }

    override suspend fun getDiary(id: String): Result<Diary> {
        return runCatching {
            diariesDao.getDiary(id)
                ?: throw IllegalArgumentException("cannot find diary of id : $id")
        }.map { localDiariesMapper.toDiary(it) }
    }

    override suspend fun saveDiary(diary: Diary): Result<Unit> {
        return runCatching { diariesDao.insertDiary(localDiariesMapper.toDiaryEntity(diary)) }
    }

    override suspend fun updateDiary(diary: Diary): Result<Unit> {
        return runCatching { diariesDao.updateDiary(localDiariesMapper.toDiaryEntity(diary)) }
    }

    override suspend fun deleteDiary(diaryId: String): Result<Unit> {
        return runCatching { diariesDao.deleteDiary(diaryId) }
    }
}

package com.malibin.study.trying.mvvm.data.local.source

import com.malibin.study.trying.mvvm.data.local.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.local.mapper.DiariesMapper
import com.malibin.study.trying.mvvm.data.source.DiariesSource
import com.malibin.study.trying.mvvm.domain.Diary

class DiariesLocalSource(
    private val diariesDao: DiariesDao,
    private val diariesMapper: DiariesMapper,
) : DiariesSource {
    override suspend fun getAllDiaries(): Result<List<Diary>> {
        return runCatching { diariesDao.getAllDiaries() }
            .map { diariesEntities -> diariesEntities.map { diariesMapper.toDiary(it) } }
    }

    override suspend fun getDiary(id: String): Result<Diary?> {
        return runCatching { diariesDao.getDiary(id) }
            .map { diariesMapper.toDiary(it ?: return@map null) }
    }

    override suspend fun saveDiary(diary: Diary): Result<Unit> {
        return runCatching { diariesDao.insertDiary(diariesMapper.toDiaryEntity(diary)) }
    }

    override suspend fun updateDiary(diary: Diary): Result<Unit> {
        return runCatching { diariesDao.updateDiary(diariesMapper.toDiaryEntity(diary)) }
    }

    override suspend fun deleteDiary(diaryId: String): Result<Unit> {
        return runCatching { diariesDao.deleteDiary(diaryId) }
    }
}

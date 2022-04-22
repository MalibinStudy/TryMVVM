package com.malibin.study.trying.mvvm.data.local.source

import com.malibin.study.trying.mvvm.data.local.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.local.entity.DiaryEntity
import com.malibin.study.trying.mvvm.data.source.DiariesSource
import com.malibin.study.trying.mvvm.domain.Diary

class DiariesLocalSource(
    private val diariesDao: DiariesDao,
) : DiariesSource {
    override suspend fun getAllDiaries(): Result<List<Diary>> {
        return runCatching { diariesDao.getAllDiaries() }
            .map { diariesEntities ->
                diariesEntities.map { Diary(it.id, it.title, it.content, it.createDate) }
            }
    }

    override suspend fun getDiary(id: String): Result<Diary?> {
        return runCatching { diariesDao.getDiary(id) }
            .map {
                if (it == null) null
                else Diary(
                    it.id,
                    it.title,
                    it.content,
                    it.createDate
                )
            }
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
        }
    }

    override suspend fun deleteDiary(diary: Diary): Result<Unit> {
        return runCatching { diariesDao.deleteDiary(diary.id) }
    }
}

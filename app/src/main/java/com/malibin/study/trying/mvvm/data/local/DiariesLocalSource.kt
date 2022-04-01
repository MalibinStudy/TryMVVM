package com.malibin.study.trying.mvvm.data.local

import com.malibin.study.trying.mvvm.data.local.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.local.entity.DiaryEntity
import com.malibin.study.trying.mvvm.data.local.executor.DispatchExecutors
import com.malibin.study.trying.mvvm.domain.Diary

class DiariesLocalSource(
    private val diariesDao: DiariesDao,
    private val dispatchExecutors: DispatchExecutors = DispatchExecutors.getInstance(),
) {
    fun getDiary(diaryId: String, onSuccess: (Diary) -> Unit, onFailure: (Throwable) -> Unit) {
        dispatchExecutors.ioThread.execute {
            try {
                val diaryEntity = diariesDao.getDiary(diaryId)
                dispatchExecutors.mainThread.execute {
                    onSuccess(diaryEntity.toDiary())
                }
            } catch (e: Exception) {
                dispatchExecutors.mainThread.execute {
                    onFailure(e)
                }
            }
        }
    }

    fun getDiary(diaryId: String, onResult: (Result<Diary>) -> Unit) {
//        dispatchExecutors.ioThread.execute {
//            val result = runCatching { diariesDao.getDiary(diaryId) }
//                .map { it.toDiary() }
//
//            dispatchExecutors.mainThread.execute {
//                onResult(result)
//            }
//        }

        dispatchExecutors.ioThread.execute {
            runCatching { diariesDao.getDiary(diaryId) }
                .map { it.toDiary() }
                .also {
                    dispatchExecutors.mainThread.execute {
                        onResult(it)
                    }
                }
        }
    }

    fun saveDiary(diary: Diary, onResult: (Result<Unit>) -> Unit) {
        dispatchExecutors.ioThread.execute {
            runCatching { diariesDao.insertDiary(diary.toDiaryEntity()) }
                .also {
                    dispatchExecutors.mainThread.execute { onResult(it) }
                }
        }
    }

    private fun Diary.toDiaryEntity(): DiaryEntity = DiaryEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        createDate = this.createDate,
    )

    private fun DiaryEntity.toDiary(): Diary = Diary(
        id = this.id,
        title = this.title,
        content = this.content,
        createDate = this.createDate,
    )
}

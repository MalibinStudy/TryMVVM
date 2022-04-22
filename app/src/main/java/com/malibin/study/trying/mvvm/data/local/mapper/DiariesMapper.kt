package com.malibin.study.trying.mvvm.data.local.mapper

import com.malibin.study.trying.mvvm.data.local.entity.DiaryEntity
import com.malibin.study.trying.mvvm.domain.Diary

class DiariesMapper {
    fun toDiaryEntity(diary: Diary): DiaryEntity = DiaryEntity(
        id = diary.id,
        title = diary.title,
        content = diary.content,
        createDate = diary.createDate,
    )

    fun toDiary(diaryEntity: DiaryEntity): Diary = Diary(
        id = diaryEntity.id,
        title = diaryEntity.title,
        content = diaryEntity.content,
        createDate = diaryEntity.createDate,
    )
}

fun Diary.toDiaryEntity(): DiaryEntity = DiaryEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    createDate = this.createDate,
)

fun DiaryEntity.toDiary(): Diary = Diary(
    id = this.id,
    title = this.title,
    content = this.content,
    createDate = this.createDate,
)

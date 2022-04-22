package com.malibin.study.trying.mvvm.data.remote.mapper

import com.malibin.study.trying.mvvm.data.remote.params.SaveDiaryParams
import com.malibin.study.trying.mvvm.data.remote.params.UpdateDiaryParams
import com.malibin.study.trying.mvvm.data.remote.response.DiaryResponse
import com.malibin.study.trying.mvvm.domain.Diary
import java.util.*

class RemoteDiariesMapper {
    fun toDiary(diaryResponse: DiaryResponse): Diary = Diary(
        id = diaryResponse.id,
        title = diaryResponse.title,
        content = diaryResponse.content,
        createDate = Date(diaryResponse.createdAt),
    )

    fun toSaveDiaryParams(diary: Diary): SaveDiaryParams = SaveDiaryParams(
        title = diary.title,
        content = diary.content,
        createdAt = diary.createDate.time,
        id = diary.id,
    )

    fun toUpdateDiaryParams(diary: Diary): UpdateDiaryParams = UpdateDiaryParams(
        title = diary.title,
        content = diary.content,
        id = diary.id,
    )
}

fun DiaryResponse.toDiary(): Diary = Diary(
    id = this.id,
    title = this.title,
    content = this.content,
    createDate = Date(this.createdAt),
)

fun Diary.toSaveDiaryParams(): SaveDiaryParams = SaveDiaryParams(
    title = this.title,
    content = this.content,
    createdAt = this.createDate.time,
    id = this.id,
)

fun Diary.toUpdateDiaryParams(): UpdateDiaryParams = UpdateDiaryParams(
    title = this.title,
    content = this.content,
    id = this.id,
)

package com.malibin.study.trying.mvvm.data.remote.service

import com.malibin.study.trying.mvvm.data.remote.params.SaveDiaryParams
import com.malibin.study.trying.mvvm.data.remote.params.StudyMemberTokenParams
import com.malibin.study.trying.mvvm.data.remote.params.UpdateDiaryParams
import com.malibin.study.trying.mvvm.data.remote.response.DiaryResponse
import com.malibin.study.trying.mvvm.data.remote.response.StudyMemberTokenResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MalibinService {

    @POST("/study/member")
    suspend fun getStudyMemberToken(
        studyMemberTokenParams: StudyMemberTokenParams,
    ): Response<StudyMemberTokenResponse>

    @GET("/diary")
    suspend fun getDiaries(
        @Header(AUTHORIZATION) token: String? = null,
    ): Response<List<DiaryResponse>>

    @GET("/diary/{id}")
    suspend fun getDiary(
        @Path(ID) diaryId: String,
    ): Response<DiaryResponse>

    @POST("/diary")
    suspend fun saveDiary(
        @Header(AUTHORIZATION) token: String? = null,
        @Body params: SaveDiaryParams,
    ): Response<Unit>

    @PUT("/diary")
    suspend fun updateDiary(
        @Body params: UpdateDiaryParams,
    ): Response<Unit>

    @DELETE("/diary")
    suspend fun deleteDiary(
        @Query(ID) diaryId: String,
    ): Response<Unit>

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val ID = "id"
        const val BASE_URL = "http://52.42.22.72:7373"

        private var instance: MalibinService? = null

        fun getInstance(): MalibinService {
            return instance ?: synchronized(this) {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create<MalibinService>()
                    .also { instance = it }
            }
        }
    }
}

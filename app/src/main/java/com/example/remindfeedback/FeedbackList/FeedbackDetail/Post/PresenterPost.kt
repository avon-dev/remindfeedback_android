package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import android.graphics.ColorSpace
import android.util.Log
import android.view.Display
import com.example.remindfeedback.FeedbackList.FeedbackDetail.ModelFeedbackDetail
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.GetAllBoard
import com.example.remindfeedback.ServerModel.getAllBoardData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PresenterPost:ContractPost.Presenter {


    lateinit override var view: ContractPost.View
    lateinit override var mContext: Context

    override fun loadItems(list: ArrayList<ModelPost>) {
    }


    override fun addItems(adapterPost: AdapterPost,comment:String) {
        var tz = TimeZone.getTimeZone("Asia/Seoul")
        var gc = GregorianCalendar(tz)
        var year = gc.get(GregorianCalendar.YEAR).toString()
        var month = gc.get(GregorianCalendar.MONTH).toString()
        var day = gc.get(GregorianCalendar.DATE).toString()
        var hour= gc.get(GregorianCalendar.HOUR).toString()
        var min = gc.get(GregorianCalendar.MINUTE).toString()
        //var sec = gc.get(GregorianCalendar.SECOND).toString()


        var modelPost:ModelPost = ModelPost("dummy", "내이름", comment, "$year 년 $month 월 $day 일 $hour 시 $min 분",1)
        adapterPost.addItem(modelPost)
        view.refresh()
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
    }

    override fun updateItems(position: Int) {
    }

    override fun typeInit(feedback_id: Int, board_id: Int) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllBoard> = apiService.GetAllBoard(feedback_id)
        register_request.enqueue(object : Callback<GetAllBoard> {
            override fun onResponse(call: Call<GetAllBoard>, response: Response<GetAllBoard>) {
                if (response.isSuccessful) {
                    val getAllBoard: GetAllBoard = response.body()!!
                    val mGetAllBoard = getAllBoard.data
                    if (mGetAllBoard != null) {
                        for (i in 0 until mGetAllBoard.size) {
                            var myList: getAllBoardData = getAllBoardData()
                            myList = mGetAllBoard[i]
                            if(myList.id == board_id){//내가 선택한 포스팅일때
                                view.setView(myList.board_category, myList.board_file1, myList.board_file2, myList.board_file3)
                            }
                            //var postData: ModelFeedbackDetail = ModelFeedbackDetail(myList.fk_feedbackId, myList.id, myList.board_category, myList.board_title, myList.createdAt)
                            view.refresh()
                        }
                    } else {
                    }

                } else {
                    Log.e("asdasdasd", "뭔가 실패함")
                }
                Log.e("GetAllBoard", "response=" + response.raw())

            }

            override fun onFailure(call: Call<GetAllBoard>, t: Throwable) {
            }
        })
    }
}
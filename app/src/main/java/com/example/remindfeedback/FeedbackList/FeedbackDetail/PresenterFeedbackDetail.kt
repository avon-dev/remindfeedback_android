package com.example.remindfeedback.FeedbackList.FeedbackDetail

import android.content.Context
import android.util.Log
import com.example.remindfeedback.FeedbackList.CreateFeedback.PickCategory.ModelPickCategory
import com.example.remindfeedback.FeedbackList.ModelFeedback
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PresenterFeedbackDetail:ContractFeedbackDetail.Presenter {


    lateinit override var view: ContractFeedbackDetail.View
    lateinit override var mContext: Context


    override fun loadItems(list: ArrayList<ModelFeedbackDetail>, adapterFeedbackDetail: AdapterFeedbackDetail, feedback_Id:Int) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllBoard> = apiService.GetAllBoard(feedback_Id)
        register_request.enqueue(object : Callback<GetAllBoard> {
            override fun onResponse(call: Call<GetAllBoard>, response: Response<GetAllBoard>) {
                if (response.isSuccessful) {


                    val getAllBoard: GetAllBoard = response.body()!!
                    val mGetAllBoard = getAllBoard.data
                    if (mGetAllBoard != null) {
                        for (i in 0 until mGetAllBoard.size) {
                            var myList: getAllBoardData = getAllBoardData()
                            myList = mGetAllBoard[i]
                            var postData: ModelFeedbackDetail = ModelFeedbackDetail(myList.board_category, myList.board_title, myList.createdAt)
                            adapterFeedbackDetail.addItem(postData)
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

    override fun addItems(list: ArrayList<ModelFeedbackDetail>,createBoardText: CreateBoardText, adapterFeedbackDetail: AdapterFeedbackDetail) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllBoard> = apiService.CreateBoardText(createBoardText)
        register_request.enqueue(object : Callback<GetAllBoard> {

            override fun onResponse(call: Call<GetAllBoard>, response: Response<GetAllBoard>) {
                if (response.isSuccessful) {
                    list.clear()
                    loadItems(list, adapterFeedbackDetail, createBoardText.feedback_id)
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<GetAllBoard>, t: Throwable) {
                //여기기서 실패가 뜨는데 이마 call모델이 달라서 그러는거같음, 근데 실패해도 별 상관없어서 새로고침 코드 여기에도 넣어둠
                list.clear()
                loadItems(list, adapterFeedbackDetail, createBoardText.feedback_id)
                view.refresh()
            }
        })
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
    }

    override fun updateItems(position: Int) {
    }


}
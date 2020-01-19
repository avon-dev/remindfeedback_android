package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import android.graphics.ColorSpace
import android.util.Log
import android.view.Display
import com.example.remindfeedback.FeedbackList.FeedbackDetail.ModelFeedbackDetail
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PresenterPost:ContractPost.Presenter {


    lateinit override var view: ContractPost.View
    lateinit override var mContext: Context

    override fun getComment(list: ArrayList<ModelComment>, adapterPost: AdapterPost, board_id: Int) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllComments> = apiService.GetAllComment(board_id)
        register_request.enqueue(object : Callback<GetAllComments> {
            override fun onResponse(call: Call<GetAllComments>, response: Response<GetAllComments>) {
                if (response.isSuccessful) {

                    val getAllComment: GetAllComments = response.body()!!
                    val mGetAllComment = getAllComment.data
                    if (mGetAllComment != null) {
                        for (i in 0 until mGetAllComment.size) {
                            var myList: getAllComment = getAllComment()
                            var mUser:commentUser = commentUser()
                            myList = mGetAllComment[i]
                            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(myList.createdAt)
                            val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
                            val dateNewFormat = sdf.format(date)

                            mUser = myList.user
                            var postData: ModelComment = ModelComment(myList.id, mUser.portrait, mUser.nickname, myList.comment_content, dateNewFormat)
                            adapterPost.addItem(postData)
                            view.refresh()
                        }
                    } else {
                    }

                } else {
                    Log.e("asdasdasd", "뭔가 실패함")
                }
                Log.e("GetAllBoard", "response=" + response.raw())

            }

            override fun onFailure(call: Call<GetAllComments>, t: Throwable) {
            }
        })
    }


    override fun addComment(adapterPost: AdapterPost,createComment: CreateComment, list: ArrayList<ModelComment>) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<CreateComment> = apiService.CreateComment(createComment)
        register_request.enqueue(object : Callback<CreateComment> {

            override fun onResponse(call: Call<CreateComment>, response: Response<CreateComment>) {
                if (response.isSuccessful) {
                    list.clear()
                        getComment(list, adapterPost, createComment.board_id)
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<CreateComment>, t: Throwable) {
                //여기기서 실패가 뜨는데 이마 call모델이 달라서 그러는거같음, 근데 실패해도 별 상관없어서 새로고침 코드 여기에도 넣어둠
                list.clear()
                getComment(list, adapterPost, createComment.board_id)
                view.refresh()
            }
        })
        view.refresh()
    }

    override fun removeItems(comment_id: Int, context: Context) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<ResponseBody> = apiService.DeleteComment(comment_id)
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.e("성공!", "딜리트 성공")
                    view.refresh()
                } else {
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("실패", t.message)
            }
        })
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
                                view.setView(myList.board_category, myList.board_file1, myList.board_file2, myList.board_file3, myList.board_title, myList.createdAt, myList.board_content)
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
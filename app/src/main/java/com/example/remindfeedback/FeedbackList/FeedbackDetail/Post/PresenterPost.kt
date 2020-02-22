package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.ColorSpace
import android.text.InputFilter
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.example.remindfeedback.FeedbackList.FeedbackDetail.ModelFeedbackDetail
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.R
import com.example.remindfeedback.ServerModel.*
import com.example.remindfeedback.etcProcess.MyProgress
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

class PresenterPost : ContractPost.Presenter {


    lateinit override var view: ContractPost.View
    lateinit override var mContext: Context
    override fun getComment(list: ArrayList<ModelComment>, adapterPost: AdapterPost, board_id: Int, last_id:Int, sort:Int) {

        var staticId = 0
        var commentLastId:Int = 0
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllComments> = apiService.GetAllComment(board_id,last_id, sort)
        register_request.enqueue(object : Callback<GetAllComments> {
            override fun onResponse(
                call: Call<GetAllComments>,
                response: Response<GetAllComments>
            ) {
                if (response.isSuccessful) {

                    val getAllComment: GetAllComments = response.body()!!
                    val mGetAllComment = getAllComment.data
                    if (mGetAllComment != null) {
                        for (i in 0 until mGetAllComment.size) {
                            var myList: getAllComment = getAllComment()
                            var mUser: commentUser = commentUser()
                            myList = mGetAllComment[i]
                            val date =
                                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(myList.createdAt)
                            val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
                            val dateNewFormat = sdf.format(date)

                            mUser = myList.user
                            var postData: ModelComment = ModelComment(
                                myList.id,
                                mUser.portrait,
                                mUser.nickname,
                                myList.comment_content,
                                dateNewFormat
                            )
                            commentLastId = myList.id
                            adapterPost.addItem(postData)
                            view.refresh()
                        }

                        //라스트 아이디를 새로고침 해줌
                        view.setCommentId(commentLastId)
                        if(staticId == commentLastId){//일단 강제로 처음에 코멘트 전부 불러오게 해놨음
                        }else{
                            view.loadItem()
                        }
                        staticId = commentLastId

                        Log.e("commentLastId", commentLastId.toString())
                        view.refresh()
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


    override fun addComment(
        adapterPost: AdapterPost,
        createComment: CreateComment,
        list: ArrayList<ModelComment>
        , sort:Int
    ) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<CreateComment> = apiService.CreateComment(createComment)
        register_request.enqueue(object : Callback<CreateComment> {

            override fun onResponse(call: Call<CreateComment>, response: Response<CreateComment>) {
                if (response.isSuccessful) {
                    list.clear()
                    getComment(list, adapterPost, createComment.board_id, 0,sort)
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<CreateComment>, t: Throwable) {
                //여기기서 실패가 뜨는데 이마 call모델이 달라서 그러는거같음, 근데 실패해도 별 상관없어서 새로고침 코드 여기에도 넣어둠
                list.clear()
                getComment(list, adapterPost, createComment.board_id, 0,sort)
                view.refresh()
            }
        })
        view.refresh()
    }

    override fun removeItems(comment_id: Int,list: ArrayList<ModelComment>,adapterPost: AdapterPost, board_id:Int, sort:Int) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetDeletedComment> = apiService.DeleteComment(comment_id)
        register_request.enqueue(object : Callback<GetDeletedComment> {

            override fun onResponse(call: Call<GetDeletedComment>, response: Response<GetDeletedComment>) {
                if (response.isSuccessful) {
                    var gComment:GetDeletedComment = response.body()!!
                    var success:Boolean = gComment.success
                    if(success == true){
                        Toast.makeText(mContext, "성공적으로 삭제했습니다.", Toast.LENGTH_LONG).show()
                        list.clear()
                        getComment(list, adapterPost, board_id,0,sort)
                    }else{
                        Toast.makeText(mContext, gComment.message, Toast.LENGTH_LONG).show()
                    }
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<GetDeletedComment>, t: Throwable) {
                Log.e("실패", t.message)
                Toast.makeText(mContext, "댓글을 삭제할수 없습니다.", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun updateItems(comment_id: Int, adapterPost: AdapterPost, curruntScript:String,list: ArrayList<ModelComment>, board_id:Int, sort:Int) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetUpdatedComment> = apiService.UpdateComment(UpdateComment(curruntScript),comment_id )
        register_request.enqueue(object : Callback<GetUpdatedComment> {

            override fun onResponse(call: Call<GetUpdatedComment>, response: Response<GetUpdatedComment>) {
                if (response.isSuccessful) {
                    var gComment:GetUpdatedComment = response.body()!!
                    var success:Boolean = gComment.success
                    if(success == true){
                        Toast.makeText(mContext, "성공적으로 수정했습니다.", Toast.LENGTH_LONG).show()
                        list.clear()
                        getComment(list, adapterPost, board_id,0, sort)
                    }else{
                        Toast.makeText(mContext, gComment.message, Toast.LENGTH_LONG).show()
                    }
                    view.refresh()
                } else {
                    Toast.makeText(mContext, "알수없는 에러가 발생했습니다.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<GetUpdatedComment>, t: Throwable) {
                Log.e("실패", t.message)
                Toast.makeText(mContext, "댓글을 수정할수 없습니다.", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun showDialog(comment_id: Int, adapterPost: AdapterPost, curruntScript:String,showText: String, params: FrameLayout.LayoutParams,list: ArrayList<ModelComment>, board_id:Int, sort:Int) {
        val container = FrameLayout(mContext)
        val et = EditText(mContext)
        params.leftMargin = mContext.resources.getDimensionPixelSize(R.dimen.dialog_margin)
        params.rightMargin = mContext.resources.getDimensionPixelSize(R.dimen.dialog_margin)
        et.setLayoutParams(params)
        et.setSingleLine(true)

        val FilterArray = arrayOfNulls<InputFilter>(1)
        FilterArray[0] = InputFilter.LengthFilter(15)
        et.setFilters(FilterArray)
        et.setText(curruntScript)
        container.addView(et)
        val alt_bld = androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle)
        alt_bld.setTitle(showText)
            .setMessage("댓글을 입력하세요")
            .setIcon(R.drawable.ic_add_black)
            .setCancelable(true)
            .setView(container)
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                val value = et.getText().toString()
                if(!value.equals("")){
                   updateItems(comment_id,adapterPost,value,list,board_id,sort)
                }else{
                    Toast.makeText(mContext, "댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            })
        val alert = alt_bld.create()
        alert.show()

        // 메세지 텍스트 변경
        var textView = alert.findViewById<TextView>(android.R.id.message)
        textView?.setTextColor(Color.BLACK)
    }




    override fun typeInit(feedback_id: Int, board_id: Int) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllBoard> = apiService.GetAllBoard(feedback_id, 0)
        register_request.enqueue(object : Callback<GetAllBoard> {
            override fun onResponse(call: Call<GetAllBoard>, response: Response<GetAllBoard>) {
                if (response.isSuccessful) {
                    val getAllBoard: GetAllBoard = response.body()!!
                    val mGetAllBoard = getAllBoard.data
                    if (mGetAllBoard != null) {
                        for (i in 0 until mGetAllBoard.size) {
                            var myList: getAllBoardData = getAllBoardData()
                            myList = mGetAllBoard[i]
                            if (myList.id == board_id) {//내가 선택한 포스팅일때
                                view.setView(
                                    myList.board_category,
                                    myList.board_file1,
                                    myList.board_file2,
                                    myList.board_file3,
                                    myList.board_title,
                                    myList.createdAt,
                                    myList.board_content
                                )
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
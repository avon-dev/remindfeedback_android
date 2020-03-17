package com.avon.remindfeedback.FeedbackList.FeedbackDetail

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.avon.remindfeedback.Network.RetrofitFactory
import com.avon.remindfeedback.ServerModel.*
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.ArrayList
import android.app.Activity



class PresenterFeedbackDetail : ContractFeedbackDetail.Presenter {


    lateinit override var view: ContractFeedbackDetail.View
    lateinit override var mContext: Context


    override fun loadItems(
        list: ArrayList<ModelFeedbackDetail>,
        adapterFeedbackDetail: AdapterFeedbackDetail,
        feedback_Id: Int,
        photoBoolean: Boolean,
        textBoolean: Boolean
    ) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val register_request: Call<GetAllBoard> = apiService.GetAllBoard(feedback_Id, 0)
        register_request.enqueue(object : Callback<GetAllBoard> {

            override fun onResponse(call: Call<GetAllBoard>, response: Response<GetAllBoard>) {
                if (response.isSuccessful) {
                    val getAllBoard: GetAllBoard = response.body()!!
                    val mGetAllBoard = getAllBoard.data
                    if (mGetAllBoard != null) {
                        list.clear()
                        for (i in 0 until mGetAllBoard.size) {
                            var myList: getAllBoardData = getAllBoardData()
                            myList = mGetAllBoard[i]

                            Log.e("머징", photoBoolean.toString()+ textBoolean.toString())
                            if(photoBoolean == true && textBoolean == true){
                                var postData: ModelFeedbackDetail = ModelFeedbackDetail(feedback_Id, myList.id, myList.board_category, myList.board_title, myList.board_content, myList.createdAt)
                                adapterFeedbackDetail.addItem(postData)
                            }else if(photoBoolean == true && textBoolean == false){
                                if(myList.board_category == 1){
                                    var postData: ModelFeedbackDetail = ModelFeedbackDetail(feedback_Id, myList.id, myList.board_category, myList.board_title, myList.board_content, myList.createdAt)
                                    adapterFeedbackDetail.addItem(postData)
                                }
                            }else if(photoBoolean == false && textBoolean == true){
                                if(myList.board_category == 0){
                                    var postData: ModelFeedbackDetail = ModelFeedbackDetail(feedback_Id, myList.id, myList.board_category, myList.board_title, myList.board_content, myList.createdAt)
                                    adapterFeedbackDetail.addItem(postData)
                                }
                            }else{
                            }

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

    override fun addTextItems(
        list: ArrayList<ModelFeedbackDetail>,
        createBoardText: CreateBoardText,
        adapterFeedbackDetail: AdapterFeedbackDetail
    ) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllBoard> = apiService.CreateBoardText(createBoardText)
        register_request.enqueue(object : Callback<GetAllBoard> {

            override fun onResponse(call: Call<GetAllBoard>, response: Response<GetAllBoard>) {
                if (response.isSuccessful) {
                    list.clear()
                    loadItems(list, adapterFeedbackDetail, createBoardText.feedback_id, true, true)
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<GetAllBoard>, t: Throwable) {
                //여기기서 실패가 뜨는데 이마 call모델이 달라서 그러는거같음, 근데 실패해도 별 상관없어서 새로고침 코드 여기에도 넣어둠
                list.clear()
                loadItems(list, adapterFeedbackDetail, createBoardText.feedback_id, true, true)
                view.refresh()
            }
        })
    }

    override fun addPictureItems(
        list: ArrayList<ModelFeedbackDetail>,
        createBoardPicture: CreateBoardPicture,
        adapterFeedbackDetail: AdapterFeedbackDetail
    ) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        lateinit var register_request: Call<GetAllBoard>

        var multiPartBody2: MultipartBody.Part? = null
        var multiPartBody3: MultipartBody.Part? = null

        val image_File1 = File(createBoardPicture.file1)
        val requestBody1 = RequestBody.create(MediaType.parse("multipart/data"), image_File1)
        val multiPartBody1 = MultipartBody.Part
            .createFormData("file1", image_File1.name, requestBody1)
        if (!createBoardPicture.file2.equals("null") && createBoardPicture.file3.equals("null")) {
            val image_File2 = File(createBoardPicture.file2)
            val requestBody2 = RequestBody.create(MediaType.parse("multipart/data"), image_File2)
            multiPartBody2 = MultipartBody.Part
                .createFormData("file2", image_File2.name, requestBody2)

            register_request = apiService
                .CreateBoardPictue(
                    RequestBody.create(
                        MediaType.parse("multipart/data"),
                        createBoardPicture.feedback_id.toString()
                    ),
                    RequestBody.create(
                        MediaType.parse("multipart/data"),
                        createBoardPicture.board_title
                    ),
                    RequestBody.create(
                        MediaType.parse("multipart/data"),
                        createBoardPicture.board_content
                    ),
                    multiPartBody1, multiPartBody2, null
                )
        }
        if (!createBoardPicture.file2.equals("null") && !createBoardPicture.file3.equals("null")) {
            val image_File2 = File(createBoardPicture.file2)
            val requestBody2 = RequestBody.create(MediaType.parse("multipart/data"), image_File2)
            multiPartBody2 = MultipartBody.Part
                .createFormData("file2", image_File2.name, requestBody2)


            val image_File3 = File(createBoardPicture.file3)
            val requestBody3 = RequestBody.create(MediaType.parse("multipart/data"), image_File3)
            multiPartBody3 = MultipartBody.Part
                .createFormData("file3", image_File3.name, requestBody3)

            register_request = apiService
                .CreateBoardPictue(
                    RequestBody.create(
                        MediaType.parse("multipart/data"),
                        createBoardPicture.feedback_id.toString()
                    ),
                    RequestBody.create(
                        MediaType.parse("multipart/data"),
                        createBoardPicture.board_title
                    ),
                    RequestBody.create(
                        MediaType.parse("multipart/data"),
                        createBoardPicture.board_content
                    ),
                    multiPartBody1, multiPartBody2, multiPartBody3
                )
        }
        if (createBoardPicture.file2.equals("null") && createBoardPicture.file3.equals("null")) {
            register_request = apiService
                .CreateBoardPictue(
                    RequestBody.create(
                        MediaType.parse("multipart/data"),
                        createBoardPicture.feedback_id.toString()
                    ),
                    RequestBody.create(
                        MediaType.parse("multipart/data"),
                        createBoardPicture.board_title
                    ),
                    RequestBody.create(
                        MediaType.parse("multipart/data"),
                        createBoardPicture.board_content
                    ),
                    multiPartBody1, null, null
                )
        }



        register_request.enqueue(object : Callback<GetAllBoard> {

            override fun onResponse(call: Call<GetAllBoard>, response: Response<GetAllBoard>) {
                if (response.isSuccessful) {
                    list.clear()
                    loadItems(list, adapterFeedbackDetail, createBoardPicture.feedback_id, true, true)
                    view.refresh()
                } else {
                }
                val StatusCode = response.code()
                Log.e("post", "Status Code : $StatusCode")
                Log.e("tag", "response=" + response.raw())

            }

            override fun onFailure(call: Call<GetAllBoard>, t: Throwable) {
                //여기기서 실패가 뜨는데 이마 call모델이 달라서 그러는거같음, 근데 실패해도 별 상관없어서 새로고침 코드 여기에도 넣어둠
                list.clear()
                loadItems(list, adapterFeedbackDetail, createBoardPicture.feedback_id, true, true)
                view.refresh()
                Log.e("tag", "response=" + t.message + "   " + t.cause)

            }
        })
    }

    override fun addVideoItems(
        list: ArrayList<ModelFeedbackDetail>,
        createboardVideo: CreateboardVideo,
        adapterFeedbackDetail: AdapterFeedbackDetail
    ) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        lateinit var register_request: Call<GetAllBoard>

        val video_File = File(createboardVideo.videofile)
        val requestBody = RequestBody.create(MediaType.parse("multipart/data"), video_File)
        val multiPartBody = MultipartBody.Part
            .createFormData("videofile", video_File.name, requestBody)
        register_request = apiService
            .CreateBoardVideo(
                RequestBody.create(
                    MediaType.parse("multipart/data"),
                    createboardVideo.feedback_id.toString()
                ),
                RequestBody.create(MediaType.parse("multipart/data"), createboardVideo.board_title),
                RequestBody.create(
                    MediaType.parse("multipart/data"),
                    createboardVideo.board_content
                ),
                multiPartBody
            )
        register_request.enqueue(object : Callback<GetAllBoard> {
            override fun onResponse(call: Call<GetAllBoard>, response: Response<GetAllBoard>) {
                if (response.isSuccessful) {
                    list.clear()
                    loadItems(list, adapterFeedbackDetail, createboardVideo.feedback_id, true, true)
                    view.refresh()
                } else {
                }
                val StatusCode = response.code()
                Log.e("post", "Status Code : $StatusCode")
                Log.e("tag", "response=" + response.raw())

            }

            override fun onFailure(call: Call<GetAllBoard>, t: Throwable) {
                //여기기서 실패가 뜨는데 이마 call모델이 달라서 그러는거같음, 근데 실패해도 별 상관없어서 새로고침 코드 여기에도 넣어둠
                list.clear()
                loadItems(list, adapterFeedbackDetail, createboardVideo.feedback_id, true, true)
                view.refresh()
                Log.e("tag", "response=" + t.message + "   " + t.cause)
            }
        })
    }

    override fun addRecordItems(
        list: ArrayList<ModelFeedbackDetail>,
        createboardRecord: CreateboardRecord,
        adapterFeedbackDetail: AdapterFeedbackDetail
    ) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        lateinit var register_request: Call<GetAllBoard>
        val record_File = File(createboardRecord.recordfile)
        val requestBody = RequestBody.create(MediaType.parse("multipart/data"), record_File)
        val multiPartBody = MultipartBody.Part
            .createFormData("recordfile", record_File.name, requestBody)
        register_request = apiService
            .CreateBoardRecord(
                RequestBody.create(
                    MediaType.parse("multipart/data"),
                    createboardRecord.feedback_id.toString()
                ),
                RequestBody.create(
                    MediaType.parse("multipart/data"),
                    createboardRecord.board_title
                ),
                RequestBody.create(
                    MediaType.parse("multipart/data"),
                    createboardRecord.board_content
                ),
                multiPartBody
            )
        register_request.enqueue(object : Callback<GetAllBoard> {
            override fun onResponse(call: Call<GetAllBoard>, response: Response<GetAllBoard>) {
                if (response.isSuccessful) {
                    list.clear()
                    loadItems(list, adapterFeedbackDetail, createboardRecord.feedback_id, true, true)
                    view.refresh()
                } else {
                }
                val StatusCode = response.code()
                Log.e("post", "Status Code : $StatusCode")
                Log.e("tag", "response=" + response.raw())
            }

            override fun onFailure(call: Call<GetAllBoard>, t: Throwable) {
                //여기기서 실패가 뜨는데 이마 call모델이 달라서 그러는거같음, 근데 실패해도 별 상관없어서 새로고침 코드 여기에도 넣어둠
                list.clear()
                loadItems(list, adapterFeedbackDetail, createboardRecord.feedback_id, true, true)
                view.refresh()
                Log.e("tag", "response=" + t.message + "   " + t.cause)
            }
        })
    }


    override fun removeItems(board_id: Int, context: Context) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<ResponseBody> = apiService.DeleteBoard(board_id)
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


    override fun updateTextItems(
        list: ArrayList<ModelFeedbackDetail>,
        board_id: Int,
        createBoardText: CreateBoardText,
        adapterFeedbackDetail: AdapterFeedbackDetail
    ) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val modifyBoard: CreateBoardText =
            CreateBoardText(board_id, createBoardText.board_title, createBoardText.board_content)
        val register_request: Call<GetAllBoard> = apiService.UpdateBoardText(board_id, modifyBoard)
        register_request.enqueue(object : Callback<GetAllBoard> {

            override fun onResponse(call: Call<GetAllBoard>, response: Response<GetAllBoard>) {
                if (response.isSuccessful) {
                    list.clear()
                    loadItems(list, adapterFeedbackDetail, createBoardText.feedback_id, true, true)
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<GetAllBoard>, t: Throwable) {
                Log.e("보드 수정 실패", t.message)
                list.clear()
                loadItems(list, adapterFeedbackDetail, createBoardText.feedback_id, true, true)
                view.refresh()
            }

        })

    }


    override fun updatePictureItems(
        list: ArrayList<ModelFeedbackDetail>,
        board_id: Int,
        createBoardPicture: CreateBoardPicture,
        adapterFeedbackDetail: AdapterFeedbackDetail
    ) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        lateinit var register_request: Call<GetAllBoard>

        var multiPartBody2: MultipartBody.Part? = null
        var multiPartBody3: MultipartBody.Part? = null

        val image_File1 = File(createBoardPicture.file1)
        val requestBody1 = RequestBody.create(MediaType.parse("multipart/data"), image_File1)
        val multiPartBody1 =
            MultipartBody.Part.createFormData("file1", image_File1.name, requestBody1)

        if (!createBoardPicture.file2.equals("null") && createBoardPicture.file3.equals("null")) {
            val image_File2 = File(createBoardPicture.file2)
            val requestBody2 = RequestBody.create(MediaType.parse("multipart/data"), image_File2)
            multiPartBody2 =
                MultipartBody.Part.createFormData("file2", image_File2.name, requestBody2)

            register_request = apiService.UpdateBoardPictureAll(
                board_id,
                RequestBody.create(
                    MediaType.parse("multipart/data"),
                    createBoardPicture.board_title
                ),
                RequestBody.create(
                    MediaType.parse("multipart/data"),
                    createBoardPicture.board_content
                ),
                true, multiPartBody1, true, multiPartBody2, true, null
            )
        }
        if (!createBoardPicture.file2.equals("null") && !createBoardPicture.file3.equals("null")) {
            val image_File2 = File(createBoardPicture.file2)
            val requestBody2 = RequestBody.create(MediaType.parse("multipart/data"), image_File2)
            multiPartBody2 =
                MultipartBody.Part.createFormData("file2", image_File2.name, requestBody2)

            val image_File3 = File(createBoardPicture.file3)
            val requestBody3 = RequestBody.create(MediaType.parse("multipart/data"), image_File3)
            multiPartBody3 =
                MultipartBody.Part.createFormData("file3", image_File3.name, requestBody3)

            register_request = apiService.UpdateBoardPictureAll(
                board_id,
                RequestBody.create(
                    MediaType.parse("multipart/data"),
                    createBoardPicture.board_title
                ),
                RequestBody.create(
                    MediaType.parse("multipart/data"),
                    createBoardPicture.board_content
                ),
                true, multiPartBody1, true, multiPartBody2, true, multiPartBody3
            )
        }
        if (createBoardPicture.file2.equals("null") && createBoardPicture.file3.equals("null")) {
            register_request = apiService.UpdateBoardPictureAll(
                board_id,
                RequestBody.create(
                    MediaType.parse("multipart/data"),
                    createBoardPicture.board_title
                ),
                RequestBody.create(
                    MediaType.parse("multipart/data"),
                    createBoardPicture.board_content
                ),
                true, multiPartBody1, true, null, true, null
            )
        }


        register_request.enqueue(object : Callback<GetAllBoard> {

            override fun onResponse(call: Call<GetAllBoard>, response: Response<GetAllBoard>) {
                if (response.isSuccessful) {
                    list.clear()
                    loadItems(list, adapterFeedbackDetail, createBoardPicture.feedback_id, true, true)
                    view.refresh()
                } else {
                }
                val StatusCode = response.code()
                Log.e("post", "Status Code : $StatusCode")
                Log.e("tag", "response=" + response.raw())

            }

            override fun onFailure(call: Call<GetAllBoard>, t: Throwable) {
                list.clear()
                loadItems(list, adapterFeedbackDetail, createBoardPicture.feedback_id, true, true)
                view.refresh()
                Log.e("tag", "response=" + t.message + "   " + t.cause)

            }
        })

    }

    override fun modifyBoardActivity(
        feedback_id: Int,
        board_id: Int,
        board_category: Int,
        board_title: String,
        board_content: String
    ) {
        view.modifyBoardActivity(feedback_id, board_id, board_category, board_title, board_content)
    }

    //완료 요청 하는부분
    override fun completeRequest(feedback_id: Int) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<ResponseBody> = apiService.CompleteRequest(feedback_id)
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(mContext, "피드백완료 요청되었습니다.", Toast.LENGTH_LONG).show()
                    view.setFeedbackComplete(1)
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("실패", t.message)
                view.refresh()
            }

        })
    }

    //요철 수락 하는부분
    override fun completeAccept(feedback_id: Int) {
        Log.e("completeAccept", "" + feedback_id)

        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<ResponseBody> = apiService.CompleteAccept(feedback_id)
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(mContext, "피드백이 완료 되었습니다.", Toast.LENGTH_LONG).show()
                    view.setFeedbackComplete(2)
                    view.refresh()
                    (mContext as Activity).finish()
                } else {
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("실패", t.message)
                view.refresh()
            }

        })

    }

    override fun completeReject(feedback_id: Int) {

        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<ResponseBody> = apiService.CompleteReject(feedback_id)
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(mContext, "피드백완료 요청이 거절되었습니다.", Toast.LENGTH_LONG).show()
                    view.setFeedbackComplete(0)
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("실패", t.message)
                view.refresh()
            }

        })
    }
}
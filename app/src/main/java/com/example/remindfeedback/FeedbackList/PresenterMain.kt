package com.example.remindfeedback.FeedbackList

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import com.example.remindfeedback.FeedbackList.CreateFeedback.PickCategory.ModelPickCategory
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.*
import com.example.remindfeedback.etcProcess.MyProgress
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class PresenterMain : ContractMain.Presenter {

    lateinit override var view: ContractMain.View
    lateinit override var context: Context

    override fun loadItems(
        list: ArrayList<ModelFeedback?>,
        adapterMainFeedback: AdapterMainFeedback,
        feedback_count: Int
    ) {

        var myProgress: MyProgress = MyProgress(context)
        myProgress.show()
        var feedback_lastid: Int = 0
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllFeedback> = apiService.GetAllFeedback(feedback_count, 20)
        register_request.enqueue(object : Callback<GetAllFeedback> {
            override fun onResponse(
                call: Call<GetAllFeedback>,
                response: Response<GetAllFeedback>
            ) {
                if (response.isSuccessful) {
                    val testItem: GetAllFeedback = response.body()!!
                    val allData: getAllData? = testItem.data
                    val mFeedback = allData!!.myFeedback
                    val mCategory = allData!!.category
                    var tag_Color: String? = null
                    if (allData != null) {
                        if (mFeedback != null) {
                            for (i in 0 until mFeedback.size) {
                                var mfl: myFeedback = myFeedback()
                                mfl = mFeedback[i]

                                var adviserUser: adviserUser = adviserUser()
                                if (mfl.user == null) { //조언자가 없을경우 공백으로표시
                                    adviserUser.nickname = ""
                                    adviserUser.portrait = ""
                                } else {
                                    adviserUser = mfl.user!!
                                }


                                val date =
                                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(mfl.write_date)
                                val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
                                val dateNewFormat = sdf.format(date)
                                if (mCategory != null) {
                                    for (j in 0 until mCategory.size) {
                                        var category_list: category = category()
                                        category_list = mCategory[j]
                                        if (category_list.category_id == mfl.category) {
                                            //카테고리가 내 카테고리 목록에 존재하면 그걸로 표시하고 포문 끝냄
                                            tag_Color = category_list.category_color
                                            break
                                        } else {
                                            //카테고리 목록에 없을시 검정색으로 표시
                                            tag_Color = "#000000"
                                        }
                                    }
                                }
                                if (tag_Color == null || mfl.complete == 2) {//피드백 완료된건 아이템에 추가하지 않음
                                } else {
                                    val addData: ModelFeedback = ModelFeedback(
                                        mfl.id,
                                        adviserUser.nickname!!, mfl.category, tag_Color, mfl.title,
                                        adviserUser.portrait!!, dateNewFormat, mfl.complete, false
                                    )
                                    adapterMainFeedback.addItem(addData)
                                }
                                feedback_lastid = mfl.id
                            }

                            view.setFeedbackCount(feedback_lastid)
                            view.refresh()
                        }
                    } else {
                    }
                } else {
                }
                Log.e("tag", "response=" + response.raw())
                myProgress.dismiss()
        }

            override fun onFailure(call: Call<GetAllFeedback>, t: Throwable) {
                myProgress.dismiss()
                Toast.makeText(context, "데이터를 불러올 수 없습니다. 개발자에게 문의 해주세요", Toast.LENGTH_SHORT).show()
                Log.e("getfeedbackError", t.message)
            }
        })
    }


    override fun loadYourItems(
        list: ArrayList<ModelFeedback?>,
        adapterMainFeedback: AdapterMainFeedback,
        feedback_count: Int
    ) {
        var myProgress: MyProgress = MyProgress(context)
        myProgress.show()
        var feedback_lastid: Int = 0
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllFeedback> = apiService.GetAllFeedback(feedback_count, 20)
        register_request.enqueue(object : Callback<GetAllFeedback> {
            override fun onResponse(
                call: Call<GetAllFeedback>,
                response: Response<GetAllFeedback>
            ) {
                if (response.isSuccessful) {
                    val testItem: GetAllFeedback = response.body()!!
                    val allData: getAllData? = testItem.data
                    val mFeedback = allData!!.yourFeedback
                    var tag_Color: String? = null
                    if (allData != null) {
                        if (mFeedback != null) {
                            for (i in 0 until mFeedback.size) {
                                var mfl: yourFeedback = yourFeedback()
                                mfl = mFeedback[i]

                                if(mfl.complete != 2){
                                    var adviserUser: adviserUser = adviserUser()
                                    if (mfl.user == null) { //조언자가 없을경우 공백으로표시
                                        adviserUser.nickname = ""
                                        adviserUser.portrait = ""
                                    } else {
                                        adviserUser = mfl.user!!
                                    }
                                    val date =
                                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(mfl.write_date)
                                    val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
                                    val dateNewFormat = sdf.format(date)
                                    val addData: ModelFeedback = ModelFeedback(
                                        mfl.id,
                                        adviserUser.nickname!!, mfl.category, "#BBBBBB", mfl.title,
                                        adviserUser.portrait!!, dateNewFormat, mfl.complete, false
                                    )
                                    adapterMainFeedback.addItem(addData)
                                    feedback_lastid = mfl.id
                                }
                            }
                            view.setFeedbackCount(feedback_lastid)
                            view.refresh()
                        }
                    } else {
                    }
                } else {
                }
                Log.e("tag", "response=" + response.raw())
                myProgress.dismiss()
            }

            override fun onFailure(call: Call<GetAllFeedback>, t: Throwable) {
                myProgress.dismiss()
                Toast.makeText(context, "데이터를 불러올 수 없습니다. 개발자에게 문의 해주세요", Toast.LENGTH_SHORT).show()
                Log.e("getfeedbackError", t.message)
            }
        })
    }

    //완료된 아이템만 불러오는 부분
    override fun loadCompleteItems(
        list: ArrayList<ModelFeedback?>,
        adapterMainFeedback: AdapterMainFeedback,
        feedback_count: Int
    ) {
        var myProgress: MyProgress = MyProgress(context)
        myProgress.show()
        var feedback_lastid: Int = 0
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllFeedback> = apiService.GetAllFeedback(feedback_count, 20)
        register_request.enqueue(object : Callback<GetAllFeedback> {
            override fun onResponse(
                call: Call<GetAllFeedback>,
                response: Response<GetAllFeedback>
            ) {
                if (response.isSuccessful) {
                    val testItem: GetAllFeedback = response.body()!!
                    val allData: getAllData? = testItem.data
                    val mFeedback = allData!!.myFeedback
                    val mCategory = allData!!.category
                    var tag_Color: String? = null
                    if (allData != null) {
                        if (mFeedback != null) {
                            for (i in 0 until mFeedback.size) {
                                var mfl: myFeedback = myFeedback()
                                mfl = mFeedback[i]

                                var adviserUser: adviserUser = adviserUser()
                                if (mfl.user == null) { //조언자가 없을경우 공백으로표시
                                    adviserUser.nickname = ""
                                    adviserUser.portrait = ""
                                } else {
                                    adviserUser = mfl.user!!
                                }
                                val date =
                                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(mfl.write_date)
                                val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
                                val dateNewFormat = sdf.format(date)
                                if (mCategory != null) {
                                    for (j in 0 until mCategory.size) {
                                        var category_list: category = category()
                                        category_list = mCategory[j]
                                        if (category_list.category_id == mfl.category) {
                                            //카테고리가 내 카테고리 목록에 존재하면 그걸로 표시하고 포문 끝냄
                                            tag_Color = category_list.category_color
                                            break
                                        } else {
                                            //카테고리 목록에 없을시 검정색으로 표시
                                            tag_Color = "#000000"
                                        }
                                    }
                                }
                                if (tag_Color == null || mfl.complete != 2) {//피드백 완료된건 아이템에 추가하지 않음
                                } else {
                                    val addData: ModelFeedback = ModelFeedback(
                                        mfl.id,
                                        adviserUser.nickname!!, mfl.category, tag_Color, mfl.title,
                                        adviserUser.portrait!!, dateNewFormat, mfl.complete, false
                                    )
                                    adapterMainFeedback.addItem(addData)
                                }
                                feedback_lastid = mfl.id
                            }

                            view.setFeedbackCount(feedback_lastid)
                            view.refresh()
                        }
                    } else {
                    }
                } else {
                }
                Log.e("tag", "response=" + response.raw())
                myProgress.dismiss()
            }

            override fun onFailure(call: Call<GetAllFeedback>, t: Throwable) {
                myProgress.dismiss()
                Toast.makeText(context, "데이터를 불러올 수 없습니다. 개발자에게 문의 해주세요", Toast.LENGTH_SHORT).show()
                Log.e("getfeedbackError", t.message)
            }
        })
    }


    override fun addItems(
        list: ArrayList<ModelFeedback?>,
        category_id: Int,
        date: String?,
        title: String,
        color: String,
        user_uid: String,
        adapterMainFeedback: AdapterMainFeedback
    ) {
        list.clear()
        val date2 = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
        val dateNewFormat = sdf.format(date2)
        val ndate: Date = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val createFeedback: CreateFeedback = CreateFeedback(user_uid, category_id, title, ndate)
        val register_request: Call<CreateFeedback> = apiService.CreateFeedback(createFeedback)
        register_request.enqueue(object : Callback<CreateFeedback> {
            override fun onResponse(
                call: Call<CreateFeedback>,
                response: Response<CreateFeedback>
            ) {
                if (response.isSuccessful) {
                    val addData: ModelFeedback =
                        ModelFeedback(
                            -1,
                            "조언자",
                            category_id,
                            color,
                            title,
                            "프로필 이미지",
                            dateNewFormat,
                            -1,
                            false
                        )
                    //adapterMainFeedback.addItem(addData)
                    //loadItems(list, adapterMainFeedback, 0)
                    view.refresh()
                } else {
                    val StatusCode = response.code()
                }
            }

            override fun onFailure(call: Call<CreateFeedback>, t: Throwable) {
            }
        })


    }

    override fun removeItems(id: Int, context: Context) {
        Log.e("리무브 테스트", "$id")
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<ResponseBody> = apiService.DeleteFeedback(id)
        register_request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.e("성공!", "딜리트 성공")
                    view.refresh()
                } else {
                    val StatusCode = response.code()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("실패", t.message)
            }
        })
    }

    // 피드백 수정
    override fun updateItems(
        list: ArrayList<ModelFeedback?>,
        item_id: Int,
        category_id: Int,
        date: String?,
        title: String,
        color: String,
        user_uid: String,
        adapterMainFeedback: AdapterMainFeedback
    ) {
        val ndate: Date = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val modifyFeedback: CreateFeedback = CreateFeedback(user_uid, category_id, title, ndate)
        val register_request: Call<CreateFeedback> =
            apiService.ModifyFeedback(item_id, modifyFeedback)
        register_request.enqueue(object : Callback<CreateFeedback> {

            override fun onResponse(
                call: Call<CreateFeedback>,
                response: Response<CreateFeedback>
            ) {
                if (response.isSuccessful) {
                    list.clear()
                    loadItems(list, adapterMainFeedback, 0)
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<CreateFeedback>, t: Throwable) {
                Log.e("피드백 수정 실패", t.message)
            }

        })
    }

    // 피드백 수정화면 띄우기
    override fun modifyFeedbackActivity(id: Int, category_id: Int, date: String?, title: String) {
        view.modifyFeedbackActivity(id, category_id, date, title)
    }

    override fun getSpinnerArray(list: ArrayList<ModelPickCategory>) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetCategory> = apiService.GetCategory()
        register_request.enqueue(object : Callback<GetCategory> {
            override fun onResponse(call: Call<GetCategory>, response: Response<GetCategory>) {
                if (response.isSuccessful) {
                    val category: GetCategory = response.body()!!
                    val mCategory = category.data
                    Log.e("asdㅁㄴㅇㅁㄴㅇ", mCategory.toString())
                    if (mCategory != null) {
                        for (i in 0 until mCategory.size) {

                            var myList: myCategory_List = myCategory_List()
                            myList = mCategory[i]
                            /*
                            var addData: ModelCategorySetting =
                                ModelCategorySetting(
                                    myList.category_id,
                                    myList.category_color,
                                    myList.category_title
                                )
                            */
                            list.add(ModelPickCategory(myList.category_id, myList.category_color, myList.category_title))
                            view.refresh()
                        }
                    } else {
                    }
                } else {
                    Log.e("asdasdasd", "뭔가 실패함")
                }
            }

            override fun onFailure(call: Call<GetCategory>, t: Throwable) {
            }
        })
    }

    override fun categoryFilter(
        category_id: Int,
        list: ArrayList<ModelFeedback?>,
        adapterMainFeedback: AdapterMainFeedback,
        feedback_count: Int,
        feedbackIngEd:Int
    ) {
        var myProgress: MyProgress = MyProgress(context)
        myProgress.show()
        var feedback_lastid: Int = 0
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllFeedback> = apiService.GetAllFeedback(feedback_count, 20)
        register_request.enqueue(object : Callback<GetAllFeedback> {
            override fun onResponse(
                call: Call<GetAllFeedback>,
                response: Response<GetAllFeedback>
            ) {
                if (response.isSuccessful) {
                    val testItem: GetAllFeedback = response.body()!!
                    val allData: getAllData? = testItem.data
                    val mFeedback = allData!!.myFeedback
                    val mCategory = allData!!.category
                    var tag_Color: String? = null
                    if (allData != null) {
                        if (mFeedback != null) {
                            for (i in 0 until mFeedback.size) {
                                var mfl: myFeedback = myFeedback()
                                mfl = mFeedback[i]

                                var adviserUser: adviserUser = adviserUser()
                                if (mfl.user == null) { //조언자가 없을경우 공백으로표시
                                    adviserUser.nickname = ""
                                    adviserUser.portrait = ""
                                } else {
                                    adviserUser = mfl.user!!
                                }
                                val date =
                                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(mfl.write_date)
                                val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
                                val dateNewFormat = sdf.format(date)
                                if (mCategory != null) {
                                    for (j in 0 until mCategory.size) {
                                        var category_list: category = category()
                                        category_list = mCategory[j]
                                        if (category_list.category_id == mfl.category) {
                                            //카테고리가 내 카테고리 목록에 존재하면 그걸로 표시하고 포문 끝냄
                                            tag_Color = category_list.category_color
                                            break
                                        } else {
                                            //카테고리 목록에 없을시 검정색으로 표시
                                            tag_Color = "#000000"
                                        }
                                    }
                                }
                                if(feedbackIngEd == 0){//진행중인것

                                    if (tag_Color == null || mfl.complete == 2 || mfl.category != category_id) {//피드백 완료된건 아이템에 추가하지 않음
                                    } else {
                                        val addData: ModelFeedback = ModelFeedback(
                                            mfl.id,
                                            adviserUser.nickname!!, mfl.category, tag_Color, mfl.title,
                                            adviserUser.portrait!!, dateNewFormat, mfl.complete, false
                                        )
                                        adapterMainFeedback.addItem(addData)
                                    }
                                }else{//진행완료인것

                                    if (tag_Color == null || mfl.complete != 2 || mfl.category != category_id) {//피드백 완료된건 아이템에 추가하지 않음
                                    } else {
                                        val addData: ModelFeedback = ModelFeedback(
                                            mfl.id,
                                            adviserUser.nickname!!, mfl.category, tag_Color, mfl.title,
                                            adviserUser.portrait!!, dateNewFormat, mfl.complete, false
                                        )
                                        adapterMainFeedback.addItem(addData)
                                    }
                                }
                                feedback_lastid = mfl.id
                            }
                            view.setFeedbackCount(feedback_lastid)
                            view.refresh()
                        }
                    } else {
                    }
                } else {
                }
                Log.e("tag", "response=" + response.raw())
                myProgress.dismiss()
            }

            override fun onFailure(call: Call<GetAllFeedback>, t: Throwable) {
                myProgress.dismiss()
                Toast.makeText(context, "데이터를 불러올 수 없습니다. 개발자에게 문의 해주세요", Toast.LENGTH_SHORT).show()
                Log.e("getfeedbackError", t.message)
            }
        })

    }

    override fun logout() {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<ResponseBody> = apiService.LogOut()
        register_request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.e("로그아웃", "성공")
                } else {
                    Log.e("asdasdasd", "뭔가 실패함")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    override fun getMe() {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val register_request: Call<GetMe> = apiService.GET_User()
        register_request.enqueue(object : Callback<GetMe> {

            override fun onResponse(call: Call<GetMe>, response: Response<GetMe>) {
                if (response.isSuccessful) {
                    var getMe:GetMe = response.body()!!
                    var myInfo:getMyInfo = getMe.data!!

                    var nickname = myInfo.nickname
                    var email = myInfo.email
                    var portrait = myInfo.portrait


                    view.setNavData(nickname!!, email, portrait!!)
                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
            }

            override fun onFailure(call: Call<GetMe>, t: Throwable) {
            }
        })

    }


}
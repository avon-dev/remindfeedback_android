package com.avon.remindfeedback.CategorySetting

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.avon.remindfeedback.Network.RetrofitFactory
import com.avon.remindfeedback.ServerModel.CreateCategory
import com.avon.remindfeedback.ServerModel.GetCategory
import com.avon.remindfeedback.ServerModel.myCategory_List
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PresenterCategorySetting : ContractCategorySetting.Presenter {


    override lateinit var view: ContractCategorySetting.View
    override lateinit var context: Context

    override fun loadItems( adapterCategorySetting: AdapterCategorySetting, list: ArrayList<ModelCategorySetting>) {
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
                            var addData: ModelCategorySetting = ModelCategorySetting(myList.category_id, myList.category_color, myList.category_title)
                            Log.e("주제", "category_id: " + myList.category_id + ", category_title: " + myList.category_title)
                            adapterCategorySetting.addItem(addData)
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

    override fun addItems(color: String, title: String, mAdapter: AdapterCategorySetting, list: ArrayList<ModelCategorySetting>) {

        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var createCategory: CreateCategory = CreateCategory(title, color)
        val register_request: Call<Object> = apiService.CreateCategory(createCategory)
        register_request.enqueue(object : Callback<Object> {

            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                if (response.isSuccessful) {
                    var jObject: JSONObject = JSONObject(Gson().toJson(response.body()))
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_LONG).show()
                    var modelCategorySetting: ModelCategorySetting = ModelCategorySetting(-1, color, title)
                    mAdapter.addItem(modelCategorySetting)
                    list.clear()
                    loadItems(mAdapter, list)
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<Object>, t: Throwable) {
            }
        })


    }

    override fun removeItems(id: Int, context: Context) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<ResponseBody> = apiService.DeleteCategory(id)
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

    override fun updateItems(list: ArrayList<ModelCategorySetting>, id: Int, color: String, title: String, adapterCategorySetting: AdapterCategorySetting) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var modifyCategory: CreateCategory = CreateCategory(title, color)
        val request: Call<GetCategory> = apiService.ModifyCategory(id, modifyCategory)
        request.enqueue(object : Callback<GetCategory> {

            override fun onResponse(call: Call<GetCategory>, response: Response<GetCategory>) {
                if (response.isSuccessful) {
                    Log.e("주제 수정", "성공")
                    list.clear()
                    loadItems(adapterCategorySetting, list)
                    view.refresh()
                }
            }

            override fun onFailure(call: Call<GetCategory>, t: Throwable) {
                Log.e("주제 수정 실패", t.message)
                // 추후 call 모델 수정 필요
                list.clear()
                loadItems(adapterCategorySetting, list)
                view.refresh()
            }

        })
    }

    //업데이트 화면을 띄움
    override fun showModifyActivity(id: Int, color: String, title: String) {
        view.showModifyActivity(id, color, title)
        Log.e("presenter (category_id)", id.toString())
    }


}
package com.example.remindfeedback.CategorySetting

import android.content.Context
import android.util.Log
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PresenterCategorySetting: ContractCategorySetting.Presenter {


    override lateinit var view: ContractCategorySetting.View
    override lateinit var context: Context

    override fun loadItems(adapterCategorySetting: AdapterCategorySetting, list: ArrayList<ModelCategorySetting>) {
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
                            var addData: ModelCategorySetting =
                                ModelCategorySetting(myList.category_id,myList.category_color, myList.category_title)
                            Log.e("category_title", myList.category_title )
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

    override fun addItems(color:String, title:String, mAdapter:AdapterCategorySetting) {

        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var createCategory:CreateCategory = CreateCategory(title, color)
        val register_request: Call<GetCategory> = apiService.CreateCategory(createCategory)
        register_request.enqueue(object : Callback<GetCategory> {

            override fun onResponse(call: Call<GetCategory>, response: Response<GetCategory>) {
                if (response.isSuccessful) {
                    var modelCategorySetting:ModelCategorySetting = ModelCategorySetting(-1, color,title )
                    mAdapter.addItem(modelCategorySetting)
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<GetCategory>, t: Throwable) {
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

    override fun updateItems(id: Int, color: String, title: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var modifyCategory: CreateCategory = CreateCategory(title, color)
        val request: Call<GetCategory> = apiService.ModifyCategory(id, modifyCategory)
        request.enqueue(object : Callback<GetCategory>{

            override fun onResponse(call: Call<GetCategory>, response: Response<GetCategory>) {
                if (response.isSuccessful){
                    Log.e("성공","수정 성공")
                    view.refresh()
                }
            }

            override fun onFailure(call: Call<GetCategory>, t: Throwable) {
                Log.e("실패", t.message)
            }

        })
    }

    //업데이트 화면을 띄움
    override fun showModifyActivity(id: Int, color: String, title: String) {
        view.showModifyActivity(id, color, title)
        Log.e("Presenter", id.toString())
    }


}
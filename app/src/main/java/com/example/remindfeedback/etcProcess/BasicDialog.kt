package com.example.remindfeedback.etcProcess

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import com.example.remindfeedback.R

//다이얼로그 띄우는 부분
class BasicDialog(val script:String
                  , val mContext:Context
                  ,val positive: () -> Unit
                  ,val nefative:()->Unit) {

    fun makeDialog(){

        var dialog = AlertDialog.Builder(mContext)
        //dialog.setTitle("친구 삭제")
        dialog.setMessage(script)
        //dialog.setIcon(R.mipmap.ic_launcher)


        var dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->
                        positive()
                    DialogInterface.BUTTON_NEGATIVE ->
                        nefative()
                }
            }
        }

        dialog.setPositiveButton("예",dialog_listener)
        dialog.setNegativeButton("아니오",dialog_listener)
        dialog.show()

    }

}
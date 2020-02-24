package com.example.remindfeedback.etcProcess

import android.app.Activity
import android.content.Context
import android.text.SpannableString
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.remindfeedback.R
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView

class TutorialFrame(var contents:String, var title:String, var view:View,var mActivity: Activity, var tap:()->Unit){

    fun mTutorial(){
        // You don't always need a sequence, and for that there's a single time tap target
        val spannedDesc = SpannableString(contents)
        /*
        spannedDesc.setSpan(
            UnderlineSpan(),
            spannedDesc.length - "TapTargetView".length,
            spannedDesc.length,
            0
        )

         */
        TapTargetView.showFor(mActivity,
            TapTarget.forView(view, title, spannedDesc)
                .cancelable(false)
                .drawShadow(true)
                .titleTextDimen(R.dimen.fab_margin)
                .tintTarget(false),
            object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView) {
                    super.onTargetClick(view)
                    tap()
                }

                override fun onOuterCircleClick(view: TapTargetView?) {
                    super.onOuterCircleClick(view)
                    Toast.makeText(
                        view!!.context,
                        "흰색 원을 터치해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onTargetDismissed(view: TapTargetView?, userInitiated: Boolean) {
                    Log.d("TapTargetViewSample", "You dismissed me :(")
                }
            })

    }
}
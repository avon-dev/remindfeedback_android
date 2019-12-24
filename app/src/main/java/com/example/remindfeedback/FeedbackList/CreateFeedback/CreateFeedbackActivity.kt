package com.example.remindfeedback.FeedbackList.CreateFeedback

import android.app.Activity
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.remindfeedback.CreateFeedback.ContractCreateFeedback
import com.example.remindfeedback.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_create_feedback.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class CreateFeedbackActivity : AppCompatActivity(), ContractCreateFeedback.View {

    private lateinit var presenterCreateFeedback: PresenterCreateFeedback
    var choosedData: Date? = null
    var stringDate:String? = null
    lateinit var ab: ActionBar
    var modifyID:Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_feedback)

        //액션바 설정
        ab = this!!.supportActionBar!!
        ab.setTitle("새로운 피드백")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        setData()

        presenterCreateFeedback = PresenterCreateFeedback().apply {
            view = this@CreateFeedbackActivity
        }
        drop_Calendar_Button.setOnClickListener(){
            if(calendarView.visibility == View.VISIBLE){
                calendarView.visibility = View.GONE
                drop_Calendar_Button.setImageResource(R.drawable.ic_down_black)

            }else{
                drop_Calendar_Button.setImageResource(R.drawable.ic_up_black)
                calendarView.visibility = View.VISIBLE
            }
        }
        //날짜 구하는 코드
        val tz = TimeZone.getTimeZone("Asia/Seoul")
        val gc = GregorianCalendar(tz)
        calendarView.visibility = View.GONE
        //달력 관련 코드
        calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH), gc.get(GregorianCalendar.DATE)))
            .setMaximumDate(CalendarDay.from(2030, 11, 31))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit();this;

        //달력에서 날짜를 선택했을때
        calendarView.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            val Year = date.year
            val Month = date.month + 1
            val Day = date.day
            val shot_Day = "$Year,$Month,$Day"
            choose_Category_Tv.text = ""+Year+"년 "+Month+"월 "+Day+"일"
            choosedData = date.date
            stringDate = SimpleDateFormat("yyyy-MM-dd").format(choosedData)
        })
        //여기까지 달력코드


    }

    // 피드백 수정시 화면에 기존 데이터 보여주기
    override fun setData() {
        if (intent.hasExtra("title")){
            ab.setTitle("피드백 수정")
            modifyID = intent.getIntExtra("id", -1)
            val title = intent.getStringExtra("title")
            val date = intent.getStringExtra("date")
            create_Feedback_Title.setText(title)
            choose_Category_Tv.setText(date)
        }else{

        }
    }

    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.create_feedback_menu, menu)
        return true
    }

    //타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when(item.itemId){
            R.id.create_Feedback_Button -> { return create_Feedback_Button() }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }

    //버튼 눌렀을때
    fun create_Feedback_Button(): Boolean {

        if(choosedData == null){
            Toast.makeText(this@CreateFeedbackActivity, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }else{
            val intent = Intent()
            if(modifyID != -1){
                intent.putExtra("id", modifyID)
                Log.e("aaaaaa", modifyID.toString())
            }
            intent.putExtra("title", create_Feedback_Title.text.toString())
            intent.putExtra("date", stringDate)

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        return true
    }

}

package com.avon.remindfeedback.FeedbackList.FeedbackDetail.CreatePost.Record

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.transition.TransitionManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.avon.remindfeedback.R
import kotlinx.android.synthetic.main.activity_record.*
import java.io.File
import java.io.IOException

@RequiresApi(api = Build.VERSION_CODES.M)
class RecordActivity : AppCompatActivity(), View.OnClickListener {

    private var mRecorder: MediaRecorder? = null
    private var mPlayer: MediaPlayer? = null
    private var recordUri: String? = null
    private var lastProgress = 0
    private val mHandler = Handler()
    private val RECORD_AUDIO_REQUEST_CODE = 101
    private var isPlaying = false
    private var isComplete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("녹음")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermissionToRecordAudio()
        }

        imgBtRecord.setOnClickListener(this)
        imgBtStop.setOnClickListener(this)
        imgViewPlay.setOnClickListener(this)

    }


    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.record_menu, menu)
        return true
    }

    //타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when (item.itemId) {
            R.id.add_Record_Button -> {
                return add_Record_Button()
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun add_Record_Button(): Boolean {
        if (isComplete == true) {
            val intent = Intent()
            intent.putExtra("recordUri", recordUri)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, "녹음을 완료해주세요.", Toast.LENGTH_SHORT).show()
        }
        return true
    }


    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.imgBtRecord -> {
                prepareRecording()
                startRecording()
            }

            R.id.imgBtStop -> {
                prepareStop()
                stopRecording()
            }

            R.id.imgViewPlay -> {
                if (!isPlaying && recordUri != null) {
                    isPlaying = true
                    startPlaying()
                } else {
                    isPlaying = false
                    stopPlaying()
                }
            }

        }
    }

    private fun prepareStop() {
        TransitionManager.beginDelayedTransition(llRecorder)
        imgBtRecord.visibility = View.VISIBLE
        imgBtStop.visibility = View.GONE
        llPlay.visibility = View.VISIBLE
    }


    private fun prepareRecording() {
        TransitionManager.beginDelayedTransition(llRecorder)
        imgBtRecord.visibility = View.GONE
        imgBtStop.visibility = View.VISIBLE
        llPlay.visibility = View.GONE
    }

    private fun stopPlaying() {
        try {
            mPlayer!!.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mPlayer = null
        //showing the play button
        imgViewPlay.setImageResource(R.drawable.ic_play_circle)
        chronometer.stop()
    }

    private fun startRecording() {
        isComplete = false
        mRecorder = MediaRecorder()
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        val root = android.os.Environment.getExternalStorageDirectory()
        val file = File(root.absolutePath + "/RemindFeedback/Audios")
        if (!file.exists()) {
            file.mkdirs()
        }

        recordUri =
            root.absolutePath + "/RemindFeedback/Audios/" + (System.currentTimeMillis().toString() + ".mp3")
        Log.d("recordUri", recordUri)
        mRecorder!!.setOutputFile(recordUri)
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            mRecorder!!.prepare()
            mRecorder!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        lastProgress = 0
        seekBar.progress = 0
        stopPlaying()
        // making the imageView a stop button starting the chronometer
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
    }


    private fun stopRecording() {
        try {
            mRecorder!!.stop()
            mRecorder!!.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mRecorder = null
        //starting the chronometer
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
        //showing the play button
        isComplete = true
        Toast.makeText(this, "성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show()
    }


    private fun startPlaying() {
        mPlayer = MediaPlayer()
        try {
            mPlayer!!.setDataSource(recordUri)
            mPlayer!!.prepare()
            mPlayer!!.start()
        } catch (e: IOException) {
            Log.e("LOG_TAG", "prepare() failed")
        }

        //making the imageView pause button
        imgViewPlay.setImageResource(R.drawable.ic_pause_circle)

        seekBar.progress = lastProgress
        mPlayer!!.seekTo(lastProgress)
        seekBar.max = mPlayer!!.duration
        seekBarUpdate()
        chronometer.start()

        mPlayer!!.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            imgViewPlay.setImageResource(R.drawable.ic_play_circle)
            isPlaying = false
            chronometer.stop()
            chronometer.base = SystemClock.elapsedRealtime()
            mPlayer!!.seekTo(0)
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mPlayer != null && fromUser) {
                    mPlayer!!.seekTo(progress)
                    chronometer.base = SystemClock.elapsedRealtime() - mPlayer!!.currentPosition
                    lastProgress = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private var runnable: Runnable = Runnable { seekBarUpdate() }

    private fun seekBarUpdate() {
        if (mPlayer != null) {
            val mCurrentPosition = mPlayer!!.currentPosition
            seekBar.progress = mCurrentPosition
            lastProgress = mCurrentPosition
        }
        mHandler.postDelayed(runnable, 100)
    }


    private fun getPermissionToRecordAudio() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid checking the build version since Context.checkSelfPermission(...) is only available in Marshmallow
        // 2) Always check for permission (even if permission has already been granted) since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), RECORD_AUDIO_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(
                    this,
                    "You must give permissions to use this app. App is exiting.",
                    Toast.LENGTH_SHORT
                ).show()
                finishAffinity()
            }
        }
    }
}

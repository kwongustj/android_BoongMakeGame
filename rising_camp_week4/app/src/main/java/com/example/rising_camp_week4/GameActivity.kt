package com.example.rising_camp_week4

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import java.util.*
import kotlin.collections.ArrayList
class GameActivity : AppCompatActivity() {

    private var state_make: Int? = null
    private var mediaPlayer: MediaPlayer? = null
    private var boongcount = 0
    private var personTake1 : Int = 0
    private var take = 1
    private var score = 0
    private var out = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_game)

        val port_btn = findViewById<AppCompatImageButton>(R.id.btn_port)
        val rbean_btn = findViewById<AppCompatImageButton>(R.id.btn_rbean)
        val hand_btn = findViewById<AppCompatImageButton>(R.id.btn_hand)

        var handler = (Handler(Looper.getMainLooper()))

        var pictureChangeArrayList1 = ArrayList<Int>()
        pictureChangeArrayList1.add(R.drawable.person1_1)
        pictureChangeArrayList1.add(R.drawable.person1_2)
        pictureChangeArrayList1.add(R.drawable.person2_1)
        pictureChangeArrayList1.add(R.drawable.person2_2)


        port_btn.setOnClickListener {
            state_make = 0 //  반죽 클릭: 0
        }
        rbean_btn.setOnClickListener {
            state_make = 1 // 팥 클릭: 1
        }
        hand_btn.setOnClickListener {
            state_make = 2 // 손 클릭: 2
        }

        Thread() { //1번 붕어빵 틀
            val btn_boong1 = findViewById<ImageView>(R.id.btn_boong1)
            boong(btn_boong1, handler)
        }.start()

        Thread() { //2번 붕어빵 틀
            val btn_boong2 = findViewById<ImageView>(R.id.btn_boong2)
            boong(btn_boong2, handler)
        }.start()

        Thread() { //3번 붕어빵 틀
            val btn_boong3 = findViewById<ImageView>(R.id.btn_boong3)
            boong(btn_boong3, handler)

        }.start()

        Thread() { //4번 붕어빵 틀
            val btn_boong4 = findViewById<ImageView>(R.id.btn_boong4)
            boong(btn_boong4, handler)
        }.start()

        Thread() { //5번 붕어빵 틀
            val btn_boong5 = findViewById<ImageView>(R.id.btn_boong5)
            boong(btn_boong5, handler)
        }.start()

        Thread() {
            val img_person = findViewById<ImageView>(R.id.person1)
            val txt_request_boong_1 = findViewById<TextView>(R.id.txt_request_boong_1)
            val arrayBoongList = arrayOf(1,2,4,3,2,5,1,4)
            var num = 0
            loop1@for( i in arrayBoongList) {
                 personTake1 = i
                 take = 1

                    while (true) {
                        if( num ==  0) {
                            for (i in 0..1) {
                                Thread.sleep(500)
                                handler.post {
                                    img_person.setBackgroundResource(pictureChangeArrayList1[i])
                                    txt_request_boong_1.setText("붕어빵 \n ${personTake1}개 주세요 ~!")
                                }
                                if(take == 0) {
                                    num = 1
                                    handler.post {
                                        txt_request_boong_1.setText("감사합니다!!!!")
                                        img_person.setBackgroundResource(pictureChangeArrayList1[0])
                                    }
                                    handler.postDelayed({
                                        img_person.setBackgroundResource(0)
                                        txt_request_boong_1.setText("")
                                    },3000)
                                    continue@loop1
                                }
                            }

                        }
                        else if (num==1) {
                            for (i in 2..3) {
                                Thread.sleep(500)
                                handler.post {
                                    img_person.setBackgroundResource(pictureChangeArrayList1[i])
                                    txt_request_boong_1.setText("붕어빵 \n ${personTake1}개 주세요 ~!")
                                }
                                if(take == 0) {
                                    num = 0
                                    handler.post {
                                        txt_request_boong_1.setText("감사합니다!!!!")
                                        img_person.setBackgroundResource(pictureChangeArrayList1[2])

                                    }
                                    handler.postDelayed({
                                        img_person.setBackgroundResource(0)
                                        txt_request_boong_1.setText("")
                                    },3000)
                                    continue@loop1
                                }
                            }
                        }
                }
            }

        }.start()

    }

    fun boong(
        btn: ImageView,
        handler: Handler
    ) {
        var state_boong = 0
        var mLastClickTime: Long = 0
        val paper_btn = findViewById<AppCompatImageButton>(R.id.btn_paper)
        val txt_score = findViewById<TextView>(R.id.txt_score)

        val img_heart1 = findViewById<ImageView>(R.id.img_heart1)
        val img_heart2 = findViewById<ImageView>(R.id.img_heart2)

        btn.setOnTouchListener { v: View, event: MotionEvent ->
            if (state_make == 0 && state_boong == 0) {
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.e("ACTION_DOWN","ACTION_DOWN")
                        handler.post {
                            btn.setBackgroundResource(R.drawable.makeboong0)
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.e("ACTION_UP","ACTION_UP")
                        state_boong = 1
                    }
                }
            }
            else if (state_make == 1 && state_boong == 1) {
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.e("ACTION_DOWN","ACTION_DOWN")
                        handler.post {
                            btn.setBackgroundResource(R.drawable.makeboong1)
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.e("ACTION_UP","ACTION_UP")
                        state_boong = 2
                    }
                }
            }
            else if (state_make == 2) {
                if(state_boong == 2 && SystemClock.elapsedRealtime() - mLastClickTime > 5000)
                {
                    when(event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            Log.e("ACTION_DOWN","ACTION_DOWN")
                            handler.post {
                                btn.setBackgroundResource(R.drawable.change)
                            }
                        }
                        MotionEvent.ACTION_UP -> {
                            Log.e("ACTION_UP","ACTION_UP")
                            handler.post {
                                btn.setBackgroundResource(R.drawable.makeboong2)
                            }
                            state_boong = 3
                            mLastClickTime = SystemClock.elapsedRealtime()
                        }
                    }
                }
                else if(state_boong == 3 && SystemClock.elapsedRealtime() - mLastClickTime > 5000)
                {
                    when(event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            Log.e("ACTION_DOWN","ACTION_DOWN")
                            handler.post {
                                btn.setBackgroundResource(R.drawable.change)
                            }
                        }
                        MotionEvent.ACTION_UP -> {
                            Log.e("ACTION_UP","ACTION_UP")
                            if(SystemClock.elapsedRealtime() - mLastClickTime > 10000) {
                                handler.post {
                                    btn.setBackgroundResource(R.drawable.makeboong5)
                                }
                                state_boong = 10
                                out += 1
                                if(out == 1) {
                                    img_heart1.visibility = View.INVISIBLE
                                }
                                else if( out == 2) {
                                    img_heart2.visibility = View.INVISIBLE
                                }
                                else if(out == 3) {
                                    val intent = Intent(this, GameOverActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                            else {
                                handler.post {
                                    btn.setBackgroundResource(R.drawable.makeboong3)
                                }
                                state_boong = 4
                                mLastClickTime = SystemClock.elapsedRealtime()
                            }
                        }
                    }
                }
                else if(state_boong == 4 && SystemClock.elapsedRealtime() - mLastClickTime > 5000)
                {
                    when(event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            Log.e("ACTION_DOWN","ACTION_DOWN")
                            handler.post {
                                btn.setBackgroundResource(R.drawable.change)
                            }
                        }
                        MotionEvent.ACTION_UP -> {
                            Log.e("ACTION_UP","ACTION_UP")
                            if(SystemClock.elapsedRealtime() - mLastClickTime > 10000) {
                                handler.post {
                                    btn.setBackgroundResource(R.drawable.makeboong5)
                                }
                                state_boong = 10
                                out += 1
                                if(out == 1) {
                                    img_heart1.visibility = View.INVISIBLE
                                }
                                else if( out == 2) {
                                    img_heart2.visibility = View.INVISIBLE
                                }
                                else if(out == 3) {
                                    val intent = Intent(this, GameOverActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                            else{
                                handler.post {
                                    btn.setBackgroundResource(R.drawable.makeboong4)
                                }
                                state_boong = 5
                                mLastClickTime = SystemClock.elapsedRealtime()
                            }
                        }
                    }
                }
                else if(state_boong == 10){ // 붕어삥 10초 이상 둬서 탔을 때
                    when(event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            handler.post {
                                btn.setBackgroundResource(R.drawable.empty)
                            }
                            state_boong = 0
                        }
                    }
                }
                else if(state_boong == 5)
                {
                    when(event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            if(SystemClock.elapsedRealtime() - mLastClickTime > 10000) {
                                handler.post {
                                    btn.setBackgroundResource(R.drawable.makeboong5)
                                }
                                state_boong = 10
                                out += 1
                                if(out == 1) {
                                    img_heart1.visibility = View.INVISIBLE
                                }
                                else if( out == 2) {
                                    img_heart2.visibility = View.INVISIBLE
                                }
                                else if(out == 3) {
                                    val intent = Intent(this, GameOverActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                            else{
                                this.boongcount += 1
                                handler.post {
                                    btn.setBackgroundResource(R.drawable.empty)
                                    if(this.boongcount == 1) paper_btn.setBackgroundResource(R.drawable.one_paper)
                                    else if (this.boongcount == 2) paper_btn.setBackgroundResource(R.drawable.two_paper)
                                    else if (this.boongcount == 3) paper_btn.setBackgroundResource(R.drawable.three_paper)
                                    else if (this.boongcount == 4) paper_btn.setBackgroundResource(R.drawable.four_paper)
                                    else if (this.boongcount == 5) paper_btn.setBackgroundResource(R.drawable.five_paper)

                                    paper_btn.setOnClickListener {
                                        if(this.personTake1 <= boongcount) {
                                            paper_btn.setBackgroundResource(R.drawable.empty_paper)
                                            take = 0
                                            boongcount = 0

                                            if (take == 0 ) {
                                                score += 10
                                                handler.post {
                                                    txt_score.setText("${score}점")
                                                }
                                            }
                                        }

                                    }
                                }
                                state_boong = 0
                            }
                        }
                    }
                }
            }
            true
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer=  MediaPlayer.create(this, R.raw.amusing_day)
        mediaPlayer?.start()
    }

}
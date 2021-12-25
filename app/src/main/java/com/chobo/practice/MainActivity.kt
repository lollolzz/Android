package com.chobo.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.Math.abs
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var timerTask : Timer? = null
        var isRunning = false
        var sec: Int = 0
        val tv: TextView = findViewById(R.id.tv_random)
        val tv_t: TextView = findViewById(R.id.tv_timer)
        val tv_p: TextView = findViewById(R.id.tv_point)
        val btn: Button = findViewById(R.id.btn_main)

        val random_box = Random()
        val num = random_box.nextInt(1001) //11로 해야 0-10사이의 숫자가 도출된다.
        tv.text = ((num.toFloat())/100).toString()// num.toFloat()을 하는 이유는 num이 Int형이기 때문
                                                  // 숫자를 문자형으로 변환시켜주는 것이 float이다.


        btn.setOnClickListener {// button을 클릭하였을 때 시간이 가는 기능을 구현하기 위한 함수, // 랜덤으로 숫자를 만들어서 text로 출력 되게하는 함수

            isRunning = !isRunning //!는 not의 의미가 있다
            if(isRunning == true) {
                timerTask = kotlin.concurrent.timer(period = 10) { // period는 주기이다.
                    sec++
                    runOnUiThread {
                        // runOnUiTread를 사용하면 실시간으로 화면이 바뀌면서 출력
                        tv_t.text = (sec.toFloat()/100).toString() // sec가 Int형이어서 .toFloat() 를 사용해주면 period = 10 소수 둘 째 자리 까지의 숫자가 나타나게 된다
                    }
                }
            }else {
                timerTask?.cancel()
                val point = abs(sec-num).toFloat()/100
                tv_p.text = point.toString()
            }
        }
    }
}
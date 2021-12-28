package com.chobo.practice

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.lang.Math.abs
import java.util.*

class MainActivity : AppCompatActivity() {

    var p_num = 3 // 참가자 수
    var k = 1     // 참가자 번호
    val point_list = mutableListOf<Float>()
    var isBlind = false

    fun start() {
        setContentView(R.layout.activity_start)
        val tv_pnum: TextView = findViewById(R.id.tv_pnum) //아이디가 겹치면 혼돈이 일어 날 수 있다// 근데 여기서 main과 start는 독립적이기 때문에 혼돈이 발생 x
        val btn_minus: TextView = findViewById(R.id.btn_minus)
        val btn_plus: TextView = findViewById(R.id.btn_plus)
        val btn_start: Button = findViewById(R.id.btn_start)
        val btn_blind: Button = findViewById(R.id.btn_blind)

        btn_blind.setOnClickListener {
            isBlind = !isBlind
            if (isBlind == true){
                btn_blind.text = "Blind 모드 ON"
            }else {
                btn_blind.text = "Blind 모드 OFF"
            }
        }
        tv_pnum.text = p_num.toString()

        btn_minus.setOnClickListener{ // btn_minus를 클릭 하였을 때 p_num이 -1 되게하는 것
            p_num --
            if(p_num == 0) {
                p_num = 1
            }
            tv_pnum.text = p_num.toString() // p_num --를 하고 tv_pnum(화면상에서 참가인원 수를 나타내는 항목)에 변경 사항을 적용시켜주는 것
        }

        btn_plus.setOnClickListener{ // btn_minus를 클릭 하였을 때 p_num이 -1 되게하는 것
            p_num ++
            tv_pnum.text = p_num.toString()
        }

        btn_start.setOnClickListener{
            main()
        }
    }

    fun main() {
        setContentView(R.layout.activity_main)

        var timerTask : Timer? = null
        var stage = 1
        var sec: Int = 0
        val tv: TextView = findViewById(R.id.tv_pnum)
        val tv_t: TextView = findViewById(R.id.tv_timer)
        val tv_p: TextView = findViewById(R.id.tv_point)
        val tv_people: TextView = findViewById(R.id.tv_pepole)
        val btn: Button = findViewById(R.id.btn_start)
        val btn_i: Button = findViewById(R.id.btn_i)
        val random_box = Random()
        val num = random_box.nextInt(1001) //11로 하면 0-10사이의 숫자가 도출된다.
        val bg_main : ConstraintLayout = findViewById(R.id.bg_main)
        val color_list = mutableListOf<String>("#32E9321E", "#32E98E1E", "#32E9C41E", "#3287E91E", "#321EBDE9", "#321E79E9", "#32651EE9")
        var color_index = k%7-1
        if (color_index == -1){
            color_index =6
        }
        val color_sel = color_list.get(color_index)

        bg_main.setBackgroundColor(Color.parseColor(color_sel))
        tv.text = ((num.toFloat())/100).toString()// num.toFloat()을 하는 이유는 num이 Int형이기 때문
        // 숫자를 문자형으로 변환시켜주는 것이 float이다.
        btn.text = "시작"
        tv_people.text = "참가자 $k"

        btn_i.setOnClickListener {
            point_list.clear()
            k = 1
            p_num = 3
            start()
        }

        btn.setOnClickListener {// button을 클릭하였을 때 시간이 가는 기능을 구현하기 위한 함수, // 랜덤으로 숫자를 만들어서 text로 출력 되게하는 함수
            stage ++
            if(stage == 2) {
                timerTask = kotlin.concurrent.timer(period = 10) { // period는 주기이다.
                    sec++
                    runOnUiThread {
                        // runOnUiTread를 사용하면 실시간으로 화면이 바뀌면서 출력
                        if(isBlind == false) {
                            tv_t.text = (sec.toFloat() / 100).toString() // sec가 Int형이어서 .toFloat() 를 사용해주면 period = 10 소수 둘 째 자리 까지의 숫자가 나타나게 된다
                        } else if(isBlind == true && stage == 2){
                            tv_t.text = "???"
                        }
                    }
                }
                btn.text = "정지"
            }else if (stage == 3) {
                tv_t.text = (sec.toFloat() / 100).toString()
                timerTask?.cancel()
                val point = abs(sec-num).toFloat()/100
                point_list.add(point) // 상단에서 선언한 val point_list = mutableListOf<Float>()을 추가해주기 위한 것

                tv_p.text = point.toString()
                btn.text = "다음"
                stage = 0
            }else if (stage == 1) {
                if (k < p_num) { // 참가자 번호가 참가자 수보다 작을 경우
                    k++
                    main()
                } else {
                    end()
                }
            }
        }
    }

    fun end() {
        setContentView(R.layout.activity_end)
        val tv_last: TextView = findViewById(R.id.tv_last)
        val tv_lpoint: TextView = findViewById(R.id.tv_lpoint)
        val btn_init: Button = findViewById(R.id.btn_init)

        // point_list를 가져 올 수 있는 이유는 함수 밖에서 먼저 선언을 해주었기 때문이다.
        // point_list.maxOrNull() 참가자 중 점수가 가장 높은 사람을 뽑아내기 위한 함수
        tv_lpoint.text = (point_list.maxOrNull()).toString()
        // tv_last에 글자를 넣을 건데, 포인트 중 최고값을 찾아서 글자로 만들어서 tv_last에 넣는다.
        var index_last = point_list.indexOf(point_list.maxOrNull())
        // index값은 시작이 0이어서 +1을 해주면 우리가아는 순서 1,2,3위 순으로 나온다
        tv_last.text = "참가자"+(index_last+1).toString()

        btn_init.setOnClickListener{
            point_list.clear() // 바로 전 게임에서 쌓였단 점수들을 초기화 시켜주는 것
            k = 1 // 게임을 다시 시작 할 때 참가자 번호를 1번으로 다시 돌리기 위한것
            p_num = 3 // 초기치로 돌려주는 것이 게임을 진행하는데 원할하기 때문에 적용 시켜준 것
            start()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
    }
}
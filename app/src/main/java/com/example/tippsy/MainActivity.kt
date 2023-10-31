package com.example.tippsy

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val INITIAL_TIP =15

private const val TAG ="MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var edamount :EditText
    private lateinit var seekBar3 :SeekBar
    private lateinit var tvnum : TextView
    private lateinit var tvtipamount :TextView
    private lateinit var tvtotalamount :TextView
    private lateinit var tvrating :TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edamount = findViewById(R.id.edamount)
        seekBar3 = findViewById(R.id.seekBar3)
        tvnum = findViewById(R.id.tvnum)
        tvtipamount = findViewById(R.id.tvtipamount)
        tvtotalamount = findViewById(R.id.tvtotalamount)
        tvrating = findViewById(R.id.tvrating)



        seekBar3.progress= INITIAL_TIP
        tvnum.text= "$INITIAL_TIP%"
        updatetvrating(INITIAL_TIP)
        seekBar3.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG ,"OnProgressChanged $p1")
                tvnum.text="$p1%"
                computationOfTip()
                updatetvrating(p1)

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        
        edamount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG ,"aft $p0")
                computationOfTip()
            }

        })

    }

    private fun updatetvrating(p1: Int) {
        val rating = when(p1){
            in 0..9 -> "Poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else -> "Amazing"
        }


        tvrating.text =rating

        val color = ArgbEvaluator().evaluate(
            p1.toFloat() / seekBar3.max,
            ContextCompat.getColor(this, R.color.Wcolor),
            ContextCompat.getColor(this, R.color.Bcolor)
        ) as Int
        tvrating.setTextColor(color)
    }

    private fun computationOfTip() {

        if(edamount.text.isEmpty()){
            tvtipamount.text=""
            tvtotalamount.text=""
            return
        }
        val baseamount=edamount.text.toString().toDouble()
        val TipPer = seekBar3.progress

        val tipAmount = baseamount * TipPer /100
        val Totalamount= baseamount + tipAmount

        tvtipamount.text = "%.2f".format(tipAmount)
        tvtotalamount.text = "%.2f".format(Totalamount)

    }
}
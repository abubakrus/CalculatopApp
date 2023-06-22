package com.example.calculatopapp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.calculatopapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val calculator: Calculator by lazy {
        Calculator()
    }
    private val isNightMode: Boolean by lazy(LazyThreadSafetyMode.NONE) {
        resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK ==
                Configuration.UI_MODE_NIGHT_YES
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListeners()
        observeData()
    }
    private fun observeData(){
        calculator.calculatorFlow.onEach {data :CalculatorModule ->
            val structure = "${data.numberFirst} ${data.operation} ${data.numberSecond}"
            binding.resultTextView.text = structure
        }.launchIn(lifecycleScope)
    }
    private fun setOnClickListeners(){
        with(binding){
            themeIcon.setOnClickListener {
                finish()
                startActivity(intent)
                if (isNightMode)AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            oneButton.setOnClickListener {
                calculator.enterNumber(number = "1")
            }
            twoButton.setOnClickListener {
                calculator.enterNumber(number = "2")
            }
            threeButton.setOnClickListener {
                calculator.enterNumber(number = "3")
            }
            fourButton.setOnClickListener {
                calculator.enterNumber(number = "4")
            }
            fiveButton.setOnClickListener {
                calculator.enterNumber(number = "5")
            }
            sixButton.setOnClickListener {
                calculator.enterNumber(number = "6")
            }
            sevenButton.setOnClickListener {
                calculator.enterNumber(number = "7")
            }
            eightButton.setOnClickListener {
                calculator.enterNumber(number = "8")
            }
            nineButton.setOnClickListener {
                calculator.enterNumber(number = "9")
            }
            zeroButton.setOnClickListener {
                calculator.enterNumber(number = "0")
            }
            plusButton.setOnClickListener {
                calculator.enterOperation("+")
            }
            minusButton.setOnClickListener {
                calculator.enterOperation("-")
            }
            multiplyButton.setOnClickListener {
                calculator.enterOperation("x")
            }
            divideButton.setOnClickListener {
                calculator.enterOperation("/")
            }
            calculateButton.setOnClickListener {
                calculator.enterCalculate()
            }
            delButton.setOnClickListener {
                calculator.enterDelete()
            }
            acButton.setOnClickListener {
                calculator.enterClear()
            }
            pointButton.setOnClickListener {
                calculator.enterDecimal()
            }
        }
    }
}
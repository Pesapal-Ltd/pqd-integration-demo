package com.pesapal.party.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.pesapal.party.databinding.ActivityMainBinding
import com.pesapal.party.model.PaymentResponse

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var PAYMENT_INT = 10001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        handleClick()
    }

    private fun handleClick() {
        binding.btnPayment.setOnClickListener {
            proceedToPay()
        }
    }

    private fun proceedToPay() {
        startSecondActivity()
    }

    private fun startSecondActivity() {
        handlePesapalSdk()
    }


    private fun handlePesapalSdk() {
        var intent = Intent("com.pesapal.mposPAYMENT_INTENT_ACTION_NAME")
        intent.putExtra("data", "10");
        intent.putExtra("extra", "ref");
        intent.putExtra("PAYMENT_INTENT", "mpesa");
        intent.putExtra("print", true)
        intent.putExtra("currency","KES")
        intent.putExtra("phone", "0702931540")
        startActivityForResult(intent, PAYMENT_INT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PAYMENT_INT) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    if (data?.extras != null) {
                        var paymentResponseString = data.getStringExtra("paymentResponse")
                        val gson = Gson()
                        var paymentResponse =
                            gson.fromJson(paymentResponseString, PaymentResponse::class.java)
                        if (paymentResponse?.isApproved == true) {
                            if (paymentResponse?.paymentType == 1) {
                                showMessage("Mpesa payment completed successful ...")
                            } else {
                                showMessage("Card payment completed successful ...")
                            }
                        } else {
                            val message =
                                "Payment declined reason code " + paymentResponse?.reasonCode
                            showMessage(message)
                        }
                    } else {
                        val message = "Payment Cancelled"
                        showMessage(message)
                    }

                }

                Activity.RESULT_CANCELED -> {
                    val message = " Payment Cancelled "
                    showMessage(message)
                }

                else -> {
                    val message = " Unable to perform payment "
                    showMessage(message)
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
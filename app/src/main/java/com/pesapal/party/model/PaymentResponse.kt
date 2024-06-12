package com.pesapal.party.model

import java.io.Serializable

data class PaymentResponse(
    var paymentType: Int? = 1, //1 for mpesa 2 for card
    var amount: String?,
    var maskedPan: String?,
    var expiryDate: String?,
    var cardHolderName: String?,
    var customerName: String?,
    var customerPhone: String?,
    var dateTime: String?,
    var cashier: String?,
    var merchantName: String?,
    var merchantLocation: String?,
    var appVisa: String?,
    var aid: String?,
    var tc: String?,
    var refNo: String?,
    var appR: String?,
    var reasonCode: String?,
    var txnId: String?,
    var isApproved: Boolean?
) : Serializable
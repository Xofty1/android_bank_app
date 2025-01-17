package com.xofty.bankapp.datasource.retrofit.model

import android.os.Parcel
import android.os.Parcelable

data class CardNumber(
    val length: Int,
    val luhn: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(length)
        parcel.writeByte(if (luhn) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CardNumber> {
        override fun createFromParcel(parcel: Parcel): CardNumber {
            return CardNumber(parcel)
        }

        override fun newArray(size: Int): Array<CardNumber?> {
            return arrayOfNulls(size)
        }
    }
}

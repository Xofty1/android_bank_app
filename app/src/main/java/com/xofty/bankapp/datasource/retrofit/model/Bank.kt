package com.xofty.bankapp.datasource.retrofit.model

import android.os.Parcel
import android.os.Parcelable

data class Bank(
    val name: String?,
    val url: String?,
    val phone: String?,
    val city: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),  // name не может быть null, так как он обязательный
        parcel.readString(),    // url может быть null
        parcel.readString(),    // phone может быть null
        parcel.readString()     // city может быть null
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)    // записываем url, который может быть null
        parcel.writeString(phone)  // записываем phone, который может быть null
        parcel.writeString(city)   // записываем city, который может быть null
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Bank> {
        override fun createFromParcel(parcel: Parcel): Bank {
            return Bank(parcel)
        }

        override fun newArray(size: Int): Array<Bank?> {
            return arrayOfNulls(size)
        }
    }
}


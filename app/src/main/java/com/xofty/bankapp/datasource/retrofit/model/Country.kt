package com.xofty.bankapp.datasource.retrofit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import android.os.Parcel

data class Country(
    val numeric: String?,
    val alpha2: String?,
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val latitude: Int,
    val longitude: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(numeric)
        parcel.writeString(alpha2)
        parcel.writeString(name)
        parcel.writeString(emoji)
        parcel.writeString(currency)
        parcel.writeInt(latitude)
        parcel.writeInt(longitude)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country(parcel)
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }
    }
}

package com.xofty.bankapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.xofty.bankapp.domain.model.Bank
import com.xofty.bankapp.domain.model.CardNumber
import com.xofty.bankapp.domain.model.Country


data class CardInfo(
    val number: CardNumber?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: Country?,
    var bank: Bank?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(CardNumber::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Country::class.java.classLoader),
        parcel.readParcelable(Bank::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(number, flags)
        parcel.writeString(scheme)
        parcel.writeString(type)
        parcel.writeString(brand)
        parcel.writeByte(if (prepaid == true) 1 else 0)
        parcel.writeParcelable(country, flags)
        parcel.writeParcelable(bank, flags)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CardInfo> {
        override fun createFromParcel(parcel: Parcel): CardInfo {
            return CardInfo(parcel)
        }

        override fun newArray(size: Int): Array<CardInfo?> {
            return arrayOfNulls(size)
        }
    }
}

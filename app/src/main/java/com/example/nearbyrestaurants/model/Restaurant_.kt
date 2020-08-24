package com.example.nearbyrestaurants.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Restaurant_(
    @SerializedName("R")
    @Expose
    var r: R?,

    @SerializedName("apikey")
    @Expose
    var apikey: String?,

    @SerializedName("id")
    @Expose
    var id: String?,

    @SerializedName("name")
    @Expose
    var name: String?,

    @SerializedName("url")
    @Expose
    var url: String?,

    @SerializedName("location")
    @Expose
    var location: Location?,

    @SerializedName("switch_to_order_menu")
    @Expose
    var switchToOrderMenu: Int?,

    @SerializedName("cuisines")
    @Expose
    var cuisines: String?,

    @SerializedName("timings")
    @Expose
    var timings: String?,

    @SerializedName("average_cost_for_two")
    @Expose
    var averageCostForTwo: Int?,

    @SerializedName("price_range")
    @Expose
    var priceRange: Int?,

    @SerializedName("currency")
    @Expose
    var currency: String?,

    @SerializedName("highlights")
    @Expose
    var highlights: List<String>?,

    @SerializedName("offers")
    @Expose
    var offers: List<Any>?,

    @SerializedName("opentable_support")
    @Expose
    var opentableSupport: Int?,

    @SerializedName("is_zomato_book_res")
    @Expose
    var isZomatoBookRes: Int?,

    @SerializedName("mezzo_provider")
    @Expose
    var mezzoProvider: String?,

    @SerializedName("is_book_form_web_view")
    @Expose
    var isBookFormWebView: Int?,

    @SerializedName("book_form_web_view_url")
    @Expose
    var bookFormWebViewUrl: String?,

    @SerializedName("book_again_url")
    @Expose
    var bookAgainUrl: String?,

    @SerializedName("thumb")
    @Expose
    var thumb: String?,

    @SerializedName("user_rating")
    @Expose
    var userRating: UserRating?,

    @SerializedName("all_reviews_count")
    @Expose
    var allReviewsCount: Int?,

    @SerializedName("photos_url")
    @Expose
    var photosUrl: String?,

    @SerializedName("photo_count")
    @Expose
    var photoCount: Int?,

    @SerializedName("menu_url")
    @Expose
    var menuUrl: String?,

    @SerializedName("featured_image")
    @Expose
    var featuredImage: String?,

    @SerializedName("has_online_delivery")
    @Expose
    var hasOnlineDelivery: Int?,

    @SerializedName("is_delivering_now")
    @Expose
    var isDeliveringNow: Int?,

    @SerializedName("store_type")
    @Expose
    var storeType: String?,

    @SerializedName("include_bogo_offers")
    @Expose
    var includeBogoOffers: Boolean?,

    @SerializedName("deeplink")
    @Expose
    var deeplink: String?,

    @SerializedName("is_table_reservation_supported")
    @Expose
    var isTableReservationSupported: Int?,

    @SerializedName("has_table_booking")
    @Expose
    var hasTableBooking: Int?,

    @SerializedName("events_url")
    @Expose
    var eventsUrl: String?,

    @SerializedName("phone_numbers")
    @Expose
    var phoneNumbers: String?,

    @SerializedName("all_reviews")
    @Expose
    var allReviews: AllReviews?,

    @SerializedName("establishment")
    @Expose
    var establishment: List<String>?,

    @SerializedName("establishment_types")
    @Expose
    var establishmentTypes: List<Any>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("r"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("location"),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.createStringArrayList(),
        TODO("offers"),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("userRating"),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        TODO("allReviews"),
        parcel.createStringArrayList(),
        TODO("establishmentTypes")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(apikey)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeValue(switchToOrderMenu)
        parcel.writeString(cuisines)
        parcel.writeString(timings)
        parcel.writeValue(averageCostForTwo)
        parcel.writeValue(priceRange)
        parcel.writeString(currency)
        parcel.writeStringList(highlights)
        parcel.writeValue(opentableSupport)
        parcel.writeValue(isZomatoBookRes)
        parcel.writeString(mezzoProvider)
        parcel.writeValue(isBookFormWebView)
        parcel.writeString(bookFormWebViewUrl)
        parcel.writeString(bookAgainUrl)
        parcel.writeString(thumb)
        parcel.writeValue(allReviewsCount)
        parcel.writeString(photosUrl)
        parcel.writeValue(photoCount)
        parcel.writeString(menuUrl)
        parcel.writeString(featuredImage)
        parcel.writeValue(hasOnlineDelivery)
        parcel.writeValue(isDeliveringNow)
        parcel.writeString(storeType)
        parcel.writeValue(includeBogoOffers)
        parcel.writeString(deeplink)
        parcel.writeValue(isTableReservationSupported)
        parcel.writeValue(hasTableBooking)
        parcel.writeString(eventsUrl)
        parcel.writeString(phoneNumbers)
        parcel.writeStringList(establishment)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant_> {
        override fun createFromParcel(parcel: Parcel): Restaurant_ {
            return Restaurant_(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant_?> {
            return arrayOfNulls(size)
        }
    }
}
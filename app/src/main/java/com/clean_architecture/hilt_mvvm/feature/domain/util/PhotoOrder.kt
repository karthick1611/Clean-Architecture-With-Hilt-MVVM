package com.clean_architecture.hilt_mvvm.feature.domain.util

sealed class PhotoOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): PhotoOrder(orderType)
    class Date(orderType: OrderType): PhotoOrder(orderType)
    class Color(orderType: OrderType): PhotoOrder(orderType)

    fun copy(orderType: OrderType): PhotoOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}

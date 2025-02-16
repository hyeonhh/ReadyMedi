package com.teammeditalk.medicalconnect.data.model.location

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class LocationResponse(
    // 딸린 자식이 있으면 @Element
    @Element
    val header: Header,
    @Element
    val body: Body,
)

@Xml(name = "header")
data class Header(
    // 자기 혼자면 @PropertyElement
    @PropertyElement
    val resultCode: String,
    @PropertyElement
    val resultMsg: String,
)

@Xml(name = "body")
data class Body(
    @Element
    val items: Items,
    @PropertyElement
    val numOfRows: Int,
    @PropertyElement
    val pageNo: Int,
    @PropertyElement
    val totalCount: Int,
)

@Xml(name = "items")
data class Items(
    @Element(name = "item")
    val hospitalItems: List<LocationItem>? = null,
)

@Xml(name = "item")
data class LocationItem(
    @PropertyElement
    val cnt: Int,
    @PropertyElement
    val distance: Double,
    @PropertyElement
    val dutyAddr: String,
    @PropertyElement
    val dutyDiv: String,
    @PropertyElement
    val dutyDivName: String,
    @PropertyElement
    val dutyEmcls: String,
    @PropertyElement
    val dutyLvkl: Int,
    @PropertyElement
    val dutyName: String,
    @PropertyElement
    val dutyTel1: String,
    @PropertyElement
    val endTime: String,
    @PropertyElement
    val hpid: String,
    @PropertyElement
    val latitude: Double,
    @PropertyElement
    val longitude: Double,
    @PropertyElement
    val rnum: Int,
    @PropertyElement
    val startTime: String,
    @PropertyElement
    val dutyFax: String? = null,
)

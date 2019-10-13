package com.shen.somebase.model

import java.io.Serializable
import java.util.ArrayList

/*
 * created by shen at 2019/10/11 11:11
 */
class SubscribeIndustry : Serializable {
    var like: List<SubscribeIndustryBean>? = null
        get() = if (field == null) ArrayList() else field
    var unlike: List<SubscribeIndustryBean>? = null
        get() = if (field == null) ArrayList() else field
}

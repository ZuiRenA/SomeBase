package com.shen.compiler.prebuilt

import com.bennyhuo.aptutils.types.ClassType

/*
* created by shen at 2019/9/2 12:32
*/
val CONTEXT = ClassType("android.content.Context")
val INTENT = ClassType("android.content.Intent")
val ACTIVITY = ClassType("androidx.appcompat.app.AppCompatActivity")
val BUNDLE = ClassType("android.os.Bundle")
val BUNDLE_UTILS = ClassType("com.shen.runtime.utils.BundleUtils")
val ACTIVITY_BUILDER = ClassType("com.shen.runtime.ActivityBuilder")
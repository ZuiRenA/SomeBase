package com.shen.compiler.activity

import com.bennyhuo.aptutils.types.packageName
import com.bennyhuo.aptutils.types.simpleName
import com.shen.compiler.activity.entity.Field
import java.util.*
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/*
* created by shen at 2019/9/2 11:26
*/
class ActivityClass(val typeElement: TypeElement) {
    val simpleName: String = typeElement.simpleName()
    val packageName: String = typeElement.packageName()

    val fields = TreeSet<Field>()

    val isAbstract = typeElement.modifiers.contains(Modifier.ABSTRACT)

    val builder = ActivityClassBuilder(this)

    val isKotlin = typeElement.getAnnotation(META_DATA) != null

    override fun toString(): String = "$packageName.$simpleName[${fields.joinToString()}]"

    companion object {
        val META_DATA: Class<out Annotation>? = Class.forName("kotlin.Metadata").asSubclass(Annotation::class.java)
    }
}
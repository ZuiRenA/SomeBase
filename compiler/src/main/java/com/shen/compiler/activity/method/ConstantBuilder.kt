package com.shen.compiler.activity.method

import com.shen.compiler.activity.ActivityClass
import com.shen.compiler.utils.camelToUnderline
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/*
* created by shen at 2019/9/2 11:40
*/
class ConstantBuilder(private val activityClass: ActivityClass) {
    fun build(typeBuilder: TypeSpec.Builder) {
        activityClass.fields.forEach { field ->
            typeBuilder.addField(FieldSpec.builder(String::class.java, field.prefix + field.name.camelToUnderline().toUpperCase(),
                Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL
            ).initializer("\$S", field.name)
                .build())
        }
    }
}
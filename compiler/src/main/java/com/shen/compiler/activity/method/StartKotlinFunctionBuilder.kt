package com.shen.compiler.activity.method

import com.shen.compiler.activity.ActivityClass
import com.shen.compiler.activity.ActivityClassBuilder
import com.shen.compiler.activity.entity.OptionalField
import com.shen.compiler.prebuilt.ACTIVITY_BUILDER
import com.shen.compiler.prebuilt.CONTEXT
import com.shen.compiler.prebuilt.INTENT
import com.squareup.kotlinpoet.*

/*
* created by shen at 2019/9/2 13:48
*/
class StartKotlinFunctionBuilder(private val activityClass: ActivityClass) {
    fun build(fileBuilder: FileSpec.Builder) {
        val name = ActivityClassBuilder.METHOD_NAME + activityClass.simpleName
        val funBuilder = FunSpec.builder(name)
            .receiver(CONTEXT.kotlin)
            .addModifiers(KModifier.PUBLIC)
            .returns(UNIT)
            .addStatement("val intent = %T(this, %T::class.java)", INTENT.kotlin, activityClass.typeElement)

        activityClass.fields.forEach {
            val nameInner = it.name
            val className = it.asKotlinTypeName()
            if (it is OptionalField) {
                funBuilder.addParameter(ParameterSpec.builder(nameInner, className).defaultValue("null").build())
            } else {
                funBuilder.addParameter(nameInner, className)
            }
            funBuilder.addStatement("intent.putExtra(%S, %L)", nameInner, nameInner)
        }

        funBuilder.addStatement("%T.INSTANCE.startActivity(this, intent)", ACTIVITY_BUILDER.kotlin)
        fileBuilder.addFunction(funBuilder.build())
    }
}
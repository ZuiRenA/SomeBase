package com.shen.compiler.activity.method

import com.shen.compiler.activity.ActivityClass
import com.shen.compiler.prebuilt.ACTIVITY
import com.shen.compiler.prebuilt.BUNDLE
import com.shen.compiler.prebuilt.INTENT
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/*
* created by shen at 2019/9/2 13:40
*/
class SaveStateMethodBuilder(private val activityClass: ActivityClass) {
    fun builder(typeBuilder: TypeSpec.Builder) {
        val methodBuilder = MethodSpec.methodBuilder("savedState")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(TypeName.VOID)
            .addParameter(ACTIVITY.java, "instance")
            .addParameter(BUNDLE.java, "outState")
            .beginControlFlow("if(instance instanceof \$T)", activityClass.typeElement)
            .addStatement("\$T typedInstance = (\$T) instance", activityClass.typeElement, activityClass.typeElement)
            .addStatement("\$T intent = new \$T()", INTENT.java, INTENT.java)

        activityClass.fields.forEach {
            val name = it.name
            if(it.isPrivate){
                methodBuilder.addStatement("intent.putExtra(\$S, typedInstance.get\$L())", name, name.capitalize())
            } else {
                methodBuilder.addStatement("intent.putExtra(\$S, typedInstance.\$L)", name, name)
            }
        }
        methodBuilder.addStatement("outState.putAll(intent.getExtras())").endControlFlow()
        typeBuilder.addMethod(methodBuilder.build())
    }
}
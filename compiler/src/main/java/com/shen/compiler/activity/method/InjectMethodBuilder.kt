package com.shen.compiler.activity.method

import com.shen.compiler.activity.ActivityClass
import com.shen.compiler.activity.entity.OptionalField
import com.shen.compiler.prebuilt.ACTIVITY
import com.shen.compiler.prebuilt.BUNDLE
import com.shen.compiler.prebuilt.BUNDLE_UTILS
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/*
* created by shen at 2019/9/2 11:58
*/
class InjectMethodBuilder(private val activityClass: ActivityClass) {
    fun build(typeBuilder: TypeSpec.Builder) {
        val injectMethodBuilder = MethodSpec.methodBuilder("inject")
            .addParameter(ACTIVITY.java, "instance")
            .addParameter(BUNDLE.java, "savedInstanceState")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(TypeName.VOID)
            .beginControlFlow("if (instance instanceof \$T)", activityClass.typeElement)
            .addStatement("\$T typeInstance = (\$T) instance", activityClass.typeElement, activityClass.typeElement)
            .addStatement("\$T extras = savedInstanceState == null ? typedInstance.getIntent().getExtras() : savedInstanceState", BUNDLE.java)
            .beginControlFlow("if (extras != null)")

        activityClass.fields.forEach {
            val name = it.name
            val typeName = it.asJavaTypeName().box()

            if (it is OptionalField) {
                injectMethodBuilder.addStatement("\$T \$LValue = \$T.<\$T>get(extras, \$S, \$L)",
                    typeName, name, BUNDLE_UTILS.java, typeName, name, it.defaultValue)
            } else {
                injectMethodBuilder.addStatement("\$T \$LValue = \$T.<\$T>get(extras, \$S",
                    typeName, name, BUNDLE_UTILS.java, typeName, name)
            }

            if (it.isPrivate) {
                injectMethodBuilder.addStatement("typedInstance.set\$L(\$LValue)", name.capitalize(), name)
            } else {
                injectMethodBuilder.addStatement("typedInstance.\$L = \$LValue", name, name)
            }
        }

        injectMethodBuilder.endControlFlow().endControlFlow()
        typeBuilder.addMethod(injectMethodBuilder.build())
    }
}
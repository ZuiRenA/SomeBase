package com.shen.compiler

import com.shen.annotations.Builder
import com.shen.annotations.Optional
import com.shen.annotations.Required
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import com.bennyhuo.aptutils.AptContext
import com.shen.compiler.activity.ActivityClass
import java.lang.Exception
import com.bennyhuo.aptutils.logger.Logger
import com.bennyhuo.aptutils.types.isSubTypeOf
import com.google.auto.service.AutoService
import com.shen.compiler.activity.entity.Field
import com.shen.compiler.activity.entity.OptionalField
import com.sun.tools.javac.code.Symbol
import javax.annotation.processing.Processor
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.tools.Diagnostic

/*
* created by shen at 2019/9/2 11:14
*/
class BuilderProcessor : AbstractProcessor() {
    private val supportedAnnotations = setOf(Builder::class.java, Required::class.java, Optional::class.java)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.RELEASE_8

    override fun getSupportedAnnotationTypes(): MutableSet<String>
            = supportedAnnotations.mapTo(HashSet(), Class<*>::getCanonicalName)

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        env: RoundEnvironment
    ): Boolean {
        val activityClass = HashMap<Element, ActivityClass>()
        env.getElementsAnnotatedWith(Builder::class.java)
            .filter { it.kind.isClass }
            .forEach {
                try {
                    if (it.asType().isSubTypeOf("androidx.appcompat.app.AppCompatActivity")) {
                        activityClass[it] = ActivityClass(it as TypeElement)
                    } else {
                        Logger.error(it, "Unsupported typeElement: ${it.simpleName}}")
                    }
                } catch (e: Exception) {
                    Logger.logParsingError(it, Builder::class.java, e)
                }
            }

        env.getElementsAnnotatedWith(Required::class.java)
            .filter { it.kind == ElementKind.FIELD }
            .forEach {
                activityClass[it.enclosingElement]?.fields?.add(Field(it as Symbol.VarSymbol)) ?:
                        Logger.error(element = it, message = "Field $it annotated as Required while " +
                                "${it.enclosedElements} not annotated")
            }

        env.getElementsAnnotatedWith(Optional::class.java)
            .filter { it.kind == ElementKind.FIELD }
            .forEach {
                activityClass[it.enclosingElement]?.fields?.add(OptionalField(it as Symbol.VarSymbol)) ?:
                        Logger.error(it, "Field $it annotated as Required while " +
                                "${it.enclosingElement} not annotated.")
            }

        activityClass.values.forEach {
            it.builder.builder(AptContext.filer)
        }

        return true
    }
}
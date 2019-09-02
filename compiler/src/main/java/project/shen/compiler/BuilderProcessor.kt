package project.shen.compiler

import com.bennyhuo.aptutils.AptContext
import com.bennyhuo.aptutils.logger.Logger
import com.bennyhuo.aptutils.types.simpleName
import project.shen.annotations.Builder
import project.shen.annotations.Optional
import project.shen.annotations.Required
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * created by shen on 2019/9/3
 * at 0:15
 **/
class BuilderProcessor: AbstractProcessor() {
    private val supportedAnnotations = setOf(Builder::class.java, Required::class.java, Optional::class.java)

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String>
            = supportedAnnotations.mapTo(HashSet(), Class<*>::getCanonicalName)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.RELEASE_7

    override fun process(annotations: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        env.getElementsAnnotatedWith(Builder::class.java).forEach {
            Logger.warn(it, it.simpleName())
        }

        return true
    }
}

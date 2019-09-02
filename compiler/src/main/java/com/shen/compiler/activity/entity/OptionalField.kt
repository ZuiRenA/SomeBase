package com.shen.compiler.activity.entity

import com.bennyhuo.aptutils.types.isSameTypeWith
import com.shen.annotations.Optional
import com.squareup.kotlinpoet.TypeName
import com.sun.tools.javac.code.Symbol
import javax.lang.model.type.TypeKind

/*
* created by shen at 2019/9/2 11:48
*/
class OptionalField(symbols: Symbol.VarSymbol): Field(symbols) {
    var defaultValue: Any? = null
        private set

    override val prefix: String = "OPTIONAL_"

    init {
        val optional = symbols.getAnnotation(Optional::class.java)
        defaultValue = when(symbols.type.kind) {
            TypeKind.BOOLEAN -> optional.booleanValue
            TypeKind.CHAR -> "'${optional.charValue}'"
            TypeKind.BYTE -> "(byte) ${optional.byteValue}"
            TypeKind.SHORT -> "(short) ${optional.shortValue}"
            TypeKind.INT -> optional.intValue
            TypeKind.LONG -> "${optional.longValue}L"
            TypeKind.FLOAT -> "${optional.floatValue}f"
            TypeKind.DOUBLE -> optional.doubleValue
            else -> if (symbols.type.isSameTypeWith(String::class.java)) {
                """"${optional.stringValue}""""
            } else {
                null
            }
        }
    }

    override fun asKotlinTypeName() = super.asKotlinTypeName().copy(true)

    override fun compareTo(other: Field): Int = if (other is OptionalField) {
        super.compareTo(other)
    } else {
        1
    }
}
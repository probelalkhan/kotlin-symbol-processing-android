package dev.belalkhan.kspcore

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType

class GenerateToJsonProcessor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {

        val symbols = resolver.getSymbolsWithAnnotation(GenerateToJson::class.qualifiedName!!)

        symbols.filterIsInstance<KSClassDeclaration>().forEach { classDeclaration ->

            generateToJsonMethod(classDeclaration)

        }

        return emptyList()
    }

    private fun generateToJsonMethod(classDeclaration: KSClassDeclaration) {
        val className = classDeclaration.simpleName.asString()
        val packageName = classDeclaration.packageName.asString()

        val file = codeGenerator.createNewFile(
            dependencies = Dependencies.ALL_FILES,
            packageName = packageName,
            fileName = "${className}ToJson"
        )

        file.writer().use { writer ->

            writer.write(
                """
                package $packageName 
                
                
                fun $className.toJson(): String {
                    return ${"\"\"\"${classDeclaration.getAllProperties().toJson()}\"\"\""}
                }
                
            """.trimIndent()
            )
        }
    }

    private fun Sequence<KSPropertyDeclaration>.toJson(): String {
        return this.joinToString(
            separator = ", ",
            prefix = "{",
            postfix = "}"
        ) { property ->
            val propertyName = property.simpleName.asString()
            val propertyType = property.type.resolve()
            when {
                propertyType.isStringOrCharType() -> """"$propertyName": "${'$'}{this.$propertyName}""""
                propertyType.isPrimitiveType() -> """"$propertyName": ${'$'}{this.$propertyName}"""
                propertyType.isCollectionType() -> handleCollectionType(propertyName, propertyType)
                propertyType.isMapType() -> handleMapType(propertyName, propertyType)
                propertyType.isAnnotatedWithGenerateToJson() -> """"$propertyName": ${'$'}{this.$propertyName.toJson()}"""
                else -> throw IllegalArgumentException("Property Name -> $propertyName is not supported for GenerateToJson")
            }
        }
    }

    private fun handleCollectionType(propertyName: String, propertyType: KSType): String {
        val elementType = propertyType.arguments.firstOrNull()?.type?.resolve()
        return if (elementType != null && elementType.isAnnotatedWithGenerateToJson()) {
            // Handle nested annotated elements in collections
            """"$propertyName": ${'$'}{this.$propertyName.joinToString(prefix = "[", postfix = "]") { it.toJson() }}"""
        } else {
            // Handle simple collections
            """"$propertyName": ${'$'}{this.$propertyName.joinToString(prefix = "[", postfix = "]") { "\"${'$'}it\"" }}"""
        }
    }

    private fun handleMapType(propertyName: String, propertyType: KSType): String {
        val keyType = propertyType.arguments.getOrNull(0)?.type?.resolve()
        val valueType = propertyType.arguments.getOrNull(1)?.type?.resolve()
        return if (valueType != null && valueType.isAnnotatedWithGenerateToJson()) {
            // Handle nested annotated values in maps
            """"$propertyName": ${'$'}{this.$propertyName.entries.joinToString(prefix = "{", postfix = "}") { "\"${'$'}{it.key}\": ${'$'}{it.value.toJson()}" }}"""
        } else {
            // Handle simple maps
            """"$propertyName": ${'$'}{this.$propertyName.entries.joinToString(prefix = "{", postfix = "}") { "\"${'$'}{it.key}\": \"${'$'}{it.value}\"" }}"""
        }
    }

    private fun KSType.isStringOrCharType(): Boolean {
        return declaration.qualifiedName?.asString() in setOf(
            "kotlin.String",
            "kotlin.Char"
        )
    }

    private fun KSType.isPrimitiveType(): Boolean {
        return declaration.qualifiedName?.asString() in setOf(
            "kotlin.Int", "kotlin.Double", "kotlin.Float", "kotlin.Long",
            "kotlin.Boolean", "kotlin.Short", "kotlin.Byte"
        )
    }


    private fun KSType.isCollectionType(): Boolean {
        return declaration.qualifiedName?.asString() in setOf(
            "kotlin.collections.List", "kotlin.collections.Set"
        )
    }

    private fun KSType.isMapType(): Boolean {
        return declaration.qualifiedName?.asString() == "kotlin.collections.Map"
    }

    private fun KSType.isAnnotatedWithGenerateToJson(): Boolean {
        val classDeclaration = declaration as? KSClassDeclaration ?: return false
        return classDeclaration.annotations.any {
            it.annotationType.resolve().declaration.qualifiedName?.asString() == GenerateToJson::class.qualifiedName
        }
    }

}
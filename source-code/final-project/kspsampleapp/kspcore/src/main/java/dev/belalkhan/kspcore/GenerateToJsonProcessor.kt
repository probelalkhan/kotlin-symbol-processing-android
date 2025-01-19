package dev.belalkhan.kspcore

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

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
            "\"$propertyName\": \"\$$propertyName\""
        }
    }

}
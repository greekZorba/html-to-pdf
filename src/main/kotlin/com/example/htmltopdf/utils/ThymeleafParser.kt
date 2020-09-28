package com.example.htmltopdf.utils

import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

object ThymeleafParser {

    fun parseHtmlFileToString(fileName: String, variableMap: Map<String, Any?>): String {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.prefix = "templates/"
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML

        val templateEngine = SpringTemplateEngine()
        templateEngine.setTemplateResolver(templateResolver)

        val context = Context()
        context.setVariables(variableMap)

        return templateEngine.process(fileName, context)
    }
}
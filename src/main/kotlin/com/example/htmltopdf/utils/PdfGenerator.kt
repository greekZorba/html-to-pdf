package com.example.htmltopdf.utils

import com.lowagie.text.pdf.BaseFont
import org.springframework.core.io.ClassPathResource
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.FileOutputStream

object PdfGenerator {

    fun generateFromHtml(filePath: String, fileName: String, html: String): String {
        val savedFilePath = "$filePath/$fileName.pdf"

        return FileOutputStream(savedFilePath).use {
            val renderer = ITextRenderer()
            renderer.sharedContext.replacedElementFactory =
                ImageReplacedElementFactory(renderer.sharedContext.replacedElementFactory)
            renderer.setDocumentFromString(html)
            renderer.fontResolver.addFont(
                ClassPathResource("/static/font/NanumBarunGothic.ttf").url.toString(),
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
            )
            renderer.layout()
            renderer.createPDF(it)

            savedFilePath
        }
    }
}
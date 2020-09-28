package com.example.htmltopdf

import com.example.htmltopdf.utils.PdfGenerator
import com.example.htmltopdf.utils.ThymeleafParser
import org.junit.jupiter.api.Test

internal class PdfGeneratorTest {

    @Test
    fun `html을 파싱해서 pdf로 저장한다`() {
        val variableMap = mapOf(
            "name" to "펭수",
            "age" to 10,
            "job" to "EBS 연습생",
            "address" to "경기도 고양시 일산동구 한류월드로 281 (장항동) EBS 소품실, 펭숙소"
        )

        val html = ThymeleafParser.parseHtmlFileToString("giant-peng", variableMap)
        PdfGenerator.generateFromHtml("/Users/jinhakkim/Desktop", "giant_peng", html)
    }
}
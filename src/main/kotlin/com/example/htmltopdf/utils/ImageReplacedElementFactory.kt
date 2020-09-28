package com.example.htmltopdf.utils

import com.lowagie.text.Image
import org.springframework.core.io.ClassPathResource
import org.w3c.dom.Element
import org.xhtmlrenderer.extend.ReplacedElement
import org.xhtmlrenderer.extend.ReplacedElementFactory
import org.xhtmlrenderer.extend.UserAgentCallback
import org.xhtmlrenderer.layout.LayoutContext
import org.xhtmlrenderer.pdf.ITextFSImage
import org.xhtmlrenderer.pdf.ITextImageElement
import org.xhtmlrenderer.render.BlockBox
import org.xhtmlrenderer.simple.extend.FormSubmissionListener
import java.nio.file.Files
import java.nio.file.Path

/**
 * resources 경로에 있는 이미지 파일을 PDF로 변환하기 위한 factory
 * */
class ImageReplacedElementFactory(
    private val replacedElementFactory: ReplacedElementFactory
) : ReplacedElementFactory {

    override fun createReplacedElement(
        layoutContext: LayoutContext?,
        blockBox: BlockBox,
        userAgentCallback: UserAgentCallback?,
        cssWidth: Int,
        cssHeight: Int
    ): ReplacedElement? {
        val element = blockBox.element ?: return null
        val nodeName = element.nodeName
        val srcPath = element.getAttribute("src")

        return if (nodeName == "img" && srcPath.startsWith("/image")) {

            val fsImage = ITextFSImage(
                Image.getInstance(
                    Files.readAllBytes(
                        Path.of(
                            ClassPathResource("static${element.getAttribute("src")}").uri
                        )
                    )
                )
            )

            if ((cssWidth != -1) || (cssHeight != -1)) {
                fsImage.scale(cssWidth, cssHeight)
            }

            ITextImageElement(fsImage)

        } else {
            replacedElementFactory.createReplacedElement(
                layoutContext,
                blockBox,
                userAgentCallback,
                cssWidth,
                cssHeight
            )
        }
    }

    override fun remove(e: Element?) {
        replacedElementFactory.remove(e)
    }

    override fun setFormSubmissionListener(listener: FormSubmissionListener?) {
        replacedElementFactory.setFormSubmissionListener(listener)
    }

    override fun reset() {
        replacedElementFactory.reset()
    }
}
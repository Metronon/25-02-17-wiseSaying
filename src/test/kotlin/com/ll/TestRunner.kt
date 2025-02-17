package com.ll

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream

object TestRunner {
    private val originalIn: InputStream = System.`in`
    private val originalOut: PrintStream = System.out

    fun run(input: String): String {
        // 마지막 종료 스크립트화
        val formattedInput = input
            .trimIndent()
            .plus("\n종료")

        return ByteArrayOutputStream().use { outputStream ->
            PrintStream(outputStream).use { printStream ->
                try {
                    System.setIn(
                        ByteArrayInputStream(
                            formattedInput.toByteArray()
                        )
                    )

                    System.setOut(printStream)

                    // 앱 실행
                    App().run()
                } finally {
                    System.setIn(originalIn)
                    System.setOut(originalOut)
                }
            }

            outputStream
                .toString()
                .trim()
                .replace("\r\n", "\n") // 개행 문자 변경
        }
    }
}

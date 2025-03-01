package com.ll.domain.wiseSaying.wiseSaying.controller

import com.ll.TestRunner
import com.ll.global.bean.SingletonScope
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test


class WiseSayingControllerTest {
    @BeforeEach
    fun setUp() {
        SingletonScope.wiseSayingRepository.clear()
    }

    @Test
    @DisplayName("명언 작성")
    fun t1() {
        val result = TestRunner.run(
            """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
        """
        )

        assertThat(result).contains("명언 : ")
        assertThat(result).contains("작가 : ")
        assertThat(result).contains("1번 명언이 등록되었습니다.")
    }

    @Test
    fun `명언 목록`() {
        val result = TestRunner.run(
            """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            등록
            천재는 99%의 노력과 1%의 영감이다.
            에디슨
            목록
        """
        )

        assertThat(result).contains("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("2 / 에디슨 / 천재는 99%의 노력과 1%의 영감이다.")
    }

    @Test
    fun `명언 삭제`() {
        val result = TestRunner.run(
            """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            등록
            천재는 99%의 노력과 1%의 영감이다.
            에디슨
            삭제?id=1
            목록
        """
        )

        assertThat(result).contains("1번 명언을 삭제하였습니다.")
        assertThat(result).doesNotContain("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("2 / 에디슨 / 천재는 99%의 노력과 1%의 영감이다.")
    }

    @Test
    fun `명언 수정`() {
        val result = TestRunner.run(
            """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            수정?id=1
            나의 죽음을 적들에게 알리지 말라. 그리고 적들에게 나의 삶을 알리라.
            이순신 장군
            목록
        """
        )

        assertThat(result).contains("1번 명언을 수정하였습니다.")
        assertThat(result).doesNotContain("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("1 / 이순신 장군 / 나의 죽음을 적들에게 알리지 말라. 그리고 적들에게 나의 삶을 알리라.")
    }

    @Test
    fun `빌드`() {
        val result = TestRunner.run(
            """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            등록
            천재는 99%의 노력과 1%의 영감이다.
            에디슨
            빌드
        """
        )

        assertThat(result)
            .contains("data.json 파일의 내용이 갱신되었습니다.");
    }
}
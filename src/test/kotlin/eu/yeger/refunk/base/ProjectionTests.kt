package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ProjectionException
import eu.yeger.refunk.shouldBe
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class ProjectionTests {

    @Test
    fun testProjection() {
        Projection(0)(5, 10, 20) shouldBe 5
        Projection(1)(5, 10, 20) shouldBe 10
        Projection(2)(5, 10, 20) shouldBe 20
    }

    @Test
    fun testProjectionException() {
        try {
            projection(-1)(10)
        } catch (e: ProjectionException) {
            return
        }
        fail<Any>()
    }
}

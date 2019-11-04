package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ProjectionException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class ProjectionTests {

    @Test
    fun testProjection() {
        assertEquals(5, Projection(0)(5, 10, 20))
        assertEquals(10, Projection(1)(5, 10, 20))
        assertEquals(20, Projection(2)(5, 10, 20))
    }

    @Test
    fun testProjectionException() {
        try {
            p(-1)(10)
        } catch (e: ProjectionException) {
            return
        }
        fail<Any>()
    }
}

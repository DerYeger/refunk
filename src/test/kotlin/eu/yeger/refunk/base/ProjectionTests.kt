package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ProjectionException
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class ProjectionTests {

    @Test
    fun testProjection() {
        assertEquals(5, Projection(0).apply(5, 10, 20))
        assertEquals(10, Projection(1).apply(5, 10, 20))
        assertEquals(20, Projection(2).apply(5, 10, 20))
    }

    @Test
    fun testProjectionException() {
        try {
            p(-1).apply(10)
        } catch (e : ProjectionException) {
            return
        }
        fail()
    }
}

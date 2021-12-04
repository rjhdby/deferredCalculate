package deferred

import Content
import ContentType
import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class DeferredCalculationTest {

    @Test
    fun `it work`() {
        val input = DeferredCalculation.of { getContent() }

        val notViewed = input
            .branch { it.filter { !it.viewed } }
            .branch { it.take(15) }
        val viewed = input
            .branch { it.filter { it.viewed } }
            .branch { it.take(15 - notViewed.calculate().size) }

        val out = notViewed.calculate().toMutableList()
        if(out.size < 15) {
            out.addAll(viewed.calculate())
        }

        println(out)
        println(out.size)
    }

    private fun getContent(): List<Content> = (0..20).map {
        Content(
            Random.nextInt().toString(16),
            Random.nextInt() and 1 == 1,
            ContentType.values().random()
        )
    }
}
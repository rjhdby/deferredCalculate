package deferred

import java.util.concurrent.atomic.AtomicInteger

class DeferredCalculation<K, T> private constructor(
    private var input: DeferredCalculation<*, K>? = null,
    private val mutator: (K) -> T,
) {
    private val refCount: AtomicInteger = AtomicInteger(0)
    private var cachedResult: T? = null

    private fun calculateAndDeref(): T {
        refCount.getAndDecrement()

        return fetch()
    }

    fun calculate(): T {
        refCount.incrementAndGet()

        return fetch()
    }

    private fun fetch(): T = when (cachedResult) {
        null -> when {
            refCount.get() < 1 -> mutator(fetchInput()).also { println("CALCULATED: $it") }
            else               -> cache().also { println("CACHED: $cachedResult") }
        }
        else -> cachedResult!!.also { println("REUSED: $it") }
    }

    private fun cache(): T {
        cachedResult = mutator(fetchInput())

        return cachedResult!!
    }

    private fun fetchInput(): K {
        return (input?.calculateAndDeref() ?: Unit as K).also {
            if (it is Unit) println("INITIALIZE ROOT")
        }
    }

    fun <R> branch(subscribeMutator: (T) -> R): DeferredCalculation<T, R> {
        refCount.getAndIncrement()

        return DeferredCalculation(input = this, mutator = subscribeMutator)
    }

    override fun toString(): String {
        return "DeferredCalculation(input=$input, mutator=${mutator.javaClass}, cachedResult=$cachedResult, refCount=$refCount)"
    }

    companion object {
        fun <T> of(initializer: () -> T): DeferredCalculation<Unit, T> {
            return DeferredCalculation(mutator = { initializer() })
        }

        fun <T> of(initializer: T): DeferredCalculation<Unit, T> {
            return DeferredCalculation(mutator = { initializer })
        }
    }
}
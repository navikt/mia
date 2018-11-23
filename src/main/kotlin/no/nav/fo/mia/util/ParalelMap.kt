package no.nav.fo.mia.util

import kotlinx.coroutines.*


fun <A, B>Iterable<A>.pmap(f: suspend (A) -> B): List<B> = runBlocking {
    map { async { f(it) } }.map { it.await() }
}

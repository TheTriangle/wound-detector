package com.teamproject.wounddetection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// Взято с https://github.com/android/codelab-kotlin-coroutines/blob/master/coroutines-codelab/finished_code/src/test/java/com/example/android/kotlincoroutines/main/utils/MainCoroutineScopeRule.kt
/**
 * MainCoroutineRule installs a TestCoroutineDispatcher for Disptachers.Main.
 *
 * Since it extends TestCoroutineScope, you can directly launch coroutines on the MainCoroutineRule
 * as a [CoroutineScope]:
 *
 * ```
 * mainCoroutineRule.launch { aTestCoroutine() }
 * ```
 *
 * All coroutines started on [MainCoroutineScopeRule] must complete (including timeouts) before the test
 * finishes, or it will throw an exception.
 *
 * When using MainCoroutineRule you should always invoke runBlockingTest on it to avoid creating two
 * instances of [TestCoroutineDispatcher] or [TestCoroutineScope] in your test:
 *
 * ```
 * @Test
 * fun usingRunBlockingTest() = mainCoroutineRule.runBlockingTest {
 *     aTestCoroutine()
 * }
 * ```
 *
 * You may call [DelayController] methods on [MainCoroutineScopeRule] and they will control the
 * virtual-clock.
 *
 * ```
 * mainCoroutineRule.pauseDispatcher()
 * // do some coroutines
 * mainCoroutineRule.advanceUntilIdle() // run all pending coroutines until the dispatcher is idle
 * ```
 *
 * By default, [MainCoroutineScopeRule] will be in a *resumed* state.
 *
 * @param dispatcher if provided, this [TestCoroutineDispatcher] will be used.
 */
@ExperimentalCoroutinesApi
class MainCoroutineScopeRule(val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestWatcher(),
    TestCoroutineScope by TestCoroutineScope(dispatcher) {
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}
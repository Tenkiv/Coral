/*
 * Copyright 2020 Tenkiv, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.tenkiv.coral

import kotlin.jvm.*

@ExperimentalCoralApi
public inline fun loop(block: LoopControl.() -> Unit) {

    while (true)
        try {
            LoopControl.instance.block()
        } catch (control: LoopControl.Break) {
            break
        }

}

@ExperimentalCoralApi
public inline fun <T> Iterator<T>.forEachLoop(operation: LoopControl.(T) -> Unit) {
    for (element in this)
        try {
            LoopControl.instance.operation(element)
        } catch (control: LoopControl.Break) {
            break
        }
}

@ExperimentalCoralApi
public inline fun <T> Iterable<T>.forEachLoop(operation: LoopControl.(T) -> Unit) {
    for (element in this)
        try {
            LoopControl.instance.operation(element)
        } catch (control: LoopControl.Break) {
            break
        }
}

@ExperimentalCoralApi
public class LoopControl private constructor() {

    @ExperimentalCoralApi
    public fun breakLoop(): Nothing = throw Break

    internal object Break : Throwable()

    companion object {
        @PublishedApi
        @JvmField
        internal val instance = LoopControl()
    }

}

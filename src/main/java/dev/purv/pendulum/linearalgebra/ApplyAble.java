package dev.purv.pendulum.linearalgebra;

import java.util.function.DoubleUnaryOperator;

public interface ApplyAble<T> {
   T apply(DoubleUnaryOperator function);
}

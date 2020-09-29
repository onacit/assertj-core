/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.core.api.double_;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.DoubleAssert;
import org.assertj.core.api.DoubleAssertBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link DoubleAssert#isNegativeInfinity()}</code>.
 * 
 * @author Jin Kwon
 */
class DoubleAssert_isNegativeInfinity_Test extends DoubleAssertBaseTest {

  @Override
  protected DoubleAssert invoke_api_method() {
    return assertions.isNegativeInfinity();
  }

  @Override
  protected void verify_internal_effects() {
    verify(doubles).assertIsNegativeInfinity(getInfo(assertions), getActual(assertions));
  }

  @Test
  void should_pass_if_actual_is_from_0xfff0000000000000L() {
    final double actual = Double.longBitsToDouble(0xfff0000000000000L);
    assertThat(actual).isNegativeInfinity();
  }

  @Test
  void should_fail_if_actual_is_positive_zero() {
    final long s = 0b0__00000000000__00000000_00000000_00000000_00000000_00000000_00000000_000L;
    final long e = 0b0__00000000000__00000000_00000000_00000000_00000000_00000000_00000000_000L;
    final long f = 0b0__00000000000__00000000_00000000_00000000_00000000_00000000_00000000_000L;
    final double actual = Double.longBitsToDouble(s | e | f);
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isNegativeInfinity())
      .withMessageContainingAll("" + actual, "" + Double.NEGATIVE_INFINITY);
  }

  @Test
  void should_fail_if_actual_is_negative_zero() {
    final long s = 0b1__00000000000__00000000_00000000_00000000_00000000_00000000_00000000_000L;
    final long e = 0b0__00000000000__00000000_00000000_00000000_00000000_00000000_00000000_000L;
    final long f = 0b0__00000000000__00000000_00000000_00000000_00000000_00000000_00000000_000L;
    final double actual = Double.longBitsToDouble(s | e | f);
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isNegativeInfinity())
      .withMessageContainingAll("" + actual, "" + Double.NEGATIVE_INFINITY);
  }

  @Test
  void should_fail_if_actual_is_subnormal_value() {
    final long s = current().nextLong(2) << 63;
    final long e = 0b0__00000000000__00000000_00000000_00000000_00000000_00000000_00000000_000L;
    final long f = (current().nextLong() >>> 12) | 0b1;
    final double actual = Double.longBitsToDouble(s | e | f);
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isNegativeInfinity())
      .withMessageContainingAll("" + actual, "" + Double.NEGATIVE_INFINITY);
  }

  @Test
  void should_fail_if_actual_is_normal_value() {
    final int s = current().nextInt(2) << 31;
    final int e = current().nextInt(1, 256) << 24;
    final int f = current().nextInt() >>> 9;
    final double actual = Double.longBitsToDouble(s | e | f);
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isNegativeInfinity())
      .withMessageContainingAll("" + actual, "" + Double.NEGATIVE_INFINITY);
  }

  @Test
  void should_fail_if_actual_is_POSITIVE_INFINITY() {
    final double actual = Double.POSITIVE_INFINITY;
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isNegativeInfinity())
      .withMessageContainingAll("" + actual, "" + Double.NEGATIVE_INFINITY);
  }

  @Test
  void should_fail_if_actual_is_NaN() {
    final double actual = Double.NaN;
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isNegativeInfinity())
      .withMessageContainingAll("" + actual, "" + Double.NEGATIVE_INFINITY);
  }

}

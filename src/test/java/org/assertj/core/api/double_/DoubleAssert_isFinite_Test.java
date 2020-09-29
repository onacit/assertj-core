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
import org.assertj.core.api.FloatAssert;
import org.assertj.core.api.FloatAssertBaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * A class for testing {@link DoubleAssert#isFinite()} method.
 * 
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class DoubleAssert_isFinite_Test extends FloatAssertBaseTest {

  @Override
  protected FloatAssert invoke_api_method() {
    return assertions.isInfinite();
  }

  @Override
  protected void verify_internal_effects() {
    verify(floats).assertIsInfinite(getInfo(assertions), getActual(assertions));
  }

  @ValueSource(longs = {
    0b0__00000000_000__00000000_00000000_00000000_00000000_00000000_00000000_0000L,
    0b1__00000000_000__00000000_00000000_00000000_00000000_00000000_00000000_0000L
  })
  @ParameterizedTest
  void should_pass_if_actual_is_zero(long s) {
    long e = 0b0__00000000_000__00000000_00000000_00000000_00000000_00000000_00000000__0000L;
    long g = 0b0__00000000_000__00000000_00000000_00000000_00000000_00000000_00000000__0000L;
    double actual = Double.longBitsToDouble(s | e | g);
    assertThat(actual).isFinite();
  }

  @ValueSource(longs = {
    0b0__00000000_000__00000000_00000000_00000000_00000000_00000000_00000000_0000L,
    0b1__00000000_000__00000000_00000000_00000000_00000000_00000000_00000000_0000L
  })
  @ParameterizedTest
  void should_pass_if_actual_is_subnormal_value(long s) {
    long e = 0b0__00000000_000__00000000_00000000_00000000_00000000_00000000_00000000_0000;
    long g = (current().nextLong(Long.MAX_VALUE) + 1) >> 12;
    double actual = Double.longBitsToDouble(s | e | g);
    assertThat(actual).isFinite();
  }

  @ValueSource(longs = {
    0b0__00000000_000__00000000_00000000_00000000_00000000_00000000_00000000_0000L,
    0b1__00000000_000__00000000_00000000_00000000_00000000_00000000_00000000_0000L
  })
  @ParameterizedTest
  void should_pass_if_actual_is_normal_value(long s) {
    long e = current().nextLong(0x001, 0x7FF) << 52;
    long g = current().nextLong() >>> 12;
    double actual = Double.longBitsToDouble(s | e | g);
    assertThat(actual).isFinite();
  }

  @Test
  void should_fail_if_actual_is_NEGATIVE_INFINITY() {
    double actual = Double.NEGATIVE_INFINITY;
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isFinite());
  }

  @Test
  void should_fail_if_actual_is_POSITIVE_INFINITY() {
    double actual = Double.POSITIVE_INFINITY;
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isFinite());
  }

  @Test
  void should_fail_if_actual_is_NaN() {
    double actual = Double.NaN;
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isFinite());
  }
}

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
package org.assertj.core.api.float_;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.FloatAssert;
import org.assertj.core.api.FloatAssertBaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests for <code>{@link FloatAssert#isNegativeInfinity()}</code>.
 * 
 * @author Jin Kwon
 */
class FloatAssert_isNegativeInfinity_Test extends FloatAssertBaseTest {

  @Override
  protected FloatAssert invoke_api_method() {
    return assertions.isNegativeInfinity();
  }

  @Override
  protected void verify_internal_effects() {
    verify(floats).assertIsNegativeInfinity(getInfo(assertions), getActual(assertions));
  }

  @Test
  void should_pass_if_actual_is_from_0xff800000() {
    final float actual = Float.intBitsToFloat(0xff800000);
    assertThat(actual).isNegativeInfinity();
  }

  @ValueSource(ints = {
    0b0__0000_0000__0000_0000_0000_0000_0000_000,
    0b1__0000_0000__0000_0000_0000_0000_0000_000
  })
  @ParameterizedTest
  void should_fail_if_actual_is_zero(final int sign) {
    final int exponent = 0b0__0000_0000__0000_0000_0000_0000_0000_000;
    final int significand = 0b0__0000_0000__0000_0000_0000_0000_0000_000;
    final float actual = Float.intBitsToFloat(sign | exponent | significand);
    assertThat(actual).isZero().isNotNaN().satisfies(v -> {
      assert !Float.isInfinite(actual);
    });
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isNegativeInfinity())
      .withMessageContainingAll("" + actual, "" + Float.NEGATIVE_INFINITY);
  }

  @ValueSource(ints = {
    0b0__0000_0000__0000_0000_0000_0000_0000_000,
    0b1__0000_0000__0000_0000_0000_0000_0000_000
  })
  @ParameterizedTest
  void should_fail_if_actual_is_subnormal_value(final int s) {
    final int e = 0b0__0000_0000__0000_0000_0000_0000_0000_000;
    final int f = 0b0__0000_0000__1111_1111_1111_1111_1111_111 & current().nextInt() | 0b1;
    final float actual = Float.intBitsToFloat(s | e | f);
    assert !Float.isNaN(actual);
    assert !Float.isInfinite(actual);
    assertThat(actual).isNotZero();
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isNegativeInfinity())
      .withMessageContainingAll("" + actual, "" + Float.NEGATIVE_INFINITY);
  }

  @ValueSource(ints = {
    0b0__0000_0000__0000_0000_0000_0000_0000_000,
    0b1__0000_0000__0000_0000_0000_0000_0000_000
  })
  void should_fail_if_actual_is_normal_value(final int sign) {
    final int exponent = 0b0__0111_1111__0000_0000_0000_0000_000 & current().nextInt();
    final int significand = 0b0__0111_1111__0000_0000_0000_0000_000 & current().nextInt();
    final float actual = Float.intBitsToFloat(sign | exponent | significand);
    assert !Float.isNaN(actual);
    assert !Float.isInfinite(actual);
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isNegativeInfinity())
      .withMessageContainingAll("" + actual, "" + Float.NEGATIVE_INFINITY);
  }

  @Test
  void should_fail_if_actual_is_POSITIVE_INFINITY() {
    final float actual = Float.POSITIVE_INFINITY;
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isNegativeInfinity())
      .withMessageContainingAll("" + actual, "" + Float.NEGATIVE_INFINITY);
  }

  @Test
  void should_fail_if_actual_is_NaN() {
    final float actual = Float.NaN;
    assertThatExceptionOfType(AssertionError.class)
      .isThrownBy(() -> assertThat(actual).isNegativeInfinity())
      .withMessageContainingAll("" + actual, "" + Float.NEGATIVE_INFINITY);
  }
}

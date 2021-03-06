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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.DoubleAssert;
import org.assertj.core.api.DoubleAssertBaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for {@link DoubleAssert#isNotNegativeInfinity()} method.
 * 
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class DoubleAssert_isNotNegativeInfinity_Test extends DoubleAssertBaseTest {

  @Override
  protected DoubleAssert invoke_api_method() {
    return assertions.isNotNegativeInfinity();
  }

  @Override
  protected void verify_internal_effects() {
    verify(doubles).assertIsNotNegativeInfinity(getInfo(assertions), getActual(assertions));
  }

  @Test
  void should_fail_if_actual_is_NEGATIVE_INFINITY() {
    final double actual = Float.NEGATIVE_INFINITY;
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(actual).isNotNegativeInfinity())
                                                   .withMessageContainingAll("" + actual, "" + Double.NEGATIVE_INFINITY);
  }

  @MethodSource({ "org.assertj.core.api.double_.DoubleAssertTestParameters#zeros" })
  @ParameterizedTest
  void should_pass_if_actual_is_zero(final double actual) {
    assertThat(actual).isNotNegativeInfinity();
  }

  @MethodSource({ "org.assertj.core.api.double_.DoubleAssertTestParameters#subnormalValues" })
  @ParameterizedTest
  void should_pass_if_actual_is_subnormal_value(final double actual) {
    assertThat(actual).isNotNegativeInfinity();
  }

  @MethodSource({ "org.assertj.core.api.double_.DoubleAssertTestParameters#normalValues" })
  @ParameterizedTest
  void should_pass_if_actual_is_normal_value(final double actual) {
    assertThat(actual).isNotNegativeInfinity();
  }

  @Test
  void should_pass_if_actual_is_POSITIVE_INFINITY() {
    final double actual = Double.POSITIVE_INFINITY;
    assertThat(actual).isNotNegativeInfinity();
  }

  @Test
  void should_pass_if_actual_is_NaN() {
    final double actual = Double.NaN;
    assertThat(actual).isNotNegativeInfinity();
  }
}

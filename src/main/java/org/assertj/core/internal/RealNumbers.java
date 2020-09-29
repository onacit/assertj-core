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
package org.assertj.core.internal;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.error.ShouldBeTrue;

import static org.assertj.core.error.ShouldBeTrue.shouldBeTrue;

/**
 * Base class of reusable assertions for real numbers (float and double).
 * 
 * @author Joel Costigliola
 */
public abstract class RealNumbers<NUMBER extends Number & Comparable<NUMBER>> extends Numbers<NUMBER> {

  public RealNumbers() {
    super();
  }

  public RealNumbers(ComparisonStrategy comparisonStrategy) {
    super(comparisonStrategy);
  }

  /**
   * Verifies that the actual value is equal to {@code NaN}.<br>
   * It does not rely on the custom comparisonStrategy (if one is set).
   * 
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @throws AssertionError if the actual value is not equal to {@code NaN}.
   */
  public void assertIsNaN(AssertionInfo info, NUMBER actual) {
    assertEqualByComparison(info, actual, NaN());
  }

  protected abstract NUMBER NaN();

  /**
   * Verifies that the actual value is not equal to {@code NaN}.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @throws AssertionError if the actual value is equal to {@code NaN}.
   */
  public void assertIsNotNaN(AssertionInfo info, NUMBER actual) {
    assertNotEqualByComparison(info, actual, NaN());
  }

  @Override
  protected boolean isGreaterThan(NUMBER value, NUMBER other) {
    return value.compareTo(other) > 0;
  }

  protected abstract boolean isFinite(NUMBER value);

  public void assertIsFinite(AssertionInfo info, NUMBER actual) {
    assertNotNull(info, actual);
    final boolean result = isFinite(actual);
    if (result)
      return;
    throw failures.failure(info, shouldBeTrue(false));
  }

  protected abstract boolean isInfinite(NUMBER value);

  public void assertIsInfinite(AssertionInfo info, NUMBER actual) {
    assertNotNull(info, actual);
    final boolean result = isInfinite(actual);
    if (result)
      return;
    throw failures.failure(info, shouldBeTrue(false));
  }

  /**
   * Returns a value represents {@code NEGATIVE_INFINITY}.
   * @return a value represents {@code NEGATIVE_INFINITY}
   */
  protected abstract NUMBER NEGATIVE_INFINITY();

  /**
   * Verifies that the actual value is equal to {@code NEGATIVE_INFINITY}.<br>
   * It does not rely on the custom comparisonStrategy (if one is set).
   *
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @throws AssertionError if the actual value is not equal to {@code NEGATIVE_INFINITY}.
   */
  public void assertIsNegativeInfinity(AssertionInfo info, NUMBER actual) {
    assertEqualByComparison(info, actual, NEGATIVE_INFINITY());
  }

  /**
   * Verifies that the actual value is not equal to {@code NEGATIVE_INFINITY}.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @throws AssertionError if the actual value is equal to {@code NEGATIVE_INFINITY}.
   */
  public void assertIsNotNegativeInfinity(AssertionInfo info, NUMBER actual) {
    assertNotEqualByComparison(info, actual, NEGATIVE_INFINITY());
  }
}

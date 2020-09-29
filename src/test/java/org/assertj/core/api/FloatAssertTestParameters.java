package org.assertj.core.api;

import static java.util.concurrent.ThreadLocalRandom.current;

import java.util.stream.IntStream;

public final class FloatAssertTestParameters {

  public static IntStream signMasks() {
    return IntStream.of(0b0__00000000__00000000_00000000_0000_000, 0b1__00000000__00000000_00000000_0000_000);
  }

  public static IntStream zeros() {
    return signMasks();
  }

  public static IntStream infinities() {
    return signMasks().map(v -> v | 0b0_11111111__00000000_00000000_0000_000);
  }

  public static IntStream subnormalValues() {
    return IntStream.range(0, 16)
      .map(i -> current().nextInt() >>> 9)
      .flatMap(g -> signMasks().map(s -> s | g))
      .filter(v -> v != -.0f && v != +.0f);
  }

  public static IntStream normalValues() {
    return IntStream.range(0, 16)
      .map(i -> (current().nextInt(0x01, 0xFF) << 23) | (current().nextInt() >>> 9))
      .flatMap(v -> signMasks().map(s -> s | v))
      .filter(v -> v != -.0f && v != +.0f);
  }

  private FloatAssertTestParameters() {
    super();
  }
}

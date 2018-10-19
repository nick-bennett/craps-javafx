package edu.cnm.deepdive.craps.model;

import static org.junit.jupiter.api.Assertions.*;

import edu.cnm.deepdive.craps.model.Game.State;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class GameTest {

  @DisplayName("Test losing come-out rolls")
  @ParameterizedTest(name = "Come-out roll = {0}")
  @ValueSource(ints = {2, 3, 12})
  void testComeOutLoss(int roll) {
    assertSame(State.LOSS, State.COME_OUT.roll(roll));
  }

  @DisplayName("Test winning come-out rolls")
  @ParameterizedTest(name = "Come-out roll = {0}")
  @ValueSource(ints = {7, 11})
  void testComeOutWin(int roll) {
    assertSame(State.WIN, State.COME_OUT.roll(roll));
  }

  @DisplayName("Test point-setting come-out rolls")
  @ParameterizedTest(name = "Come-out roll = {0}")
  @ValueSource(ints = {4, 5, 6, 8, 9, 10})
  void testComeOutPoint(int roll) {
    assertSame(State.POINT, State.COME_OUT.roll(roll));
  }

  @DisplayName("Test point rolls with no change of state")
  @ParameterizedTest(name = "Roll = {0}, point = {1}")
  @MethodSource("pointPointProvider")
  void testPointPoint(int roll, int point) {
    assertSame(State.POINT, State.POINT.roll(roll, point));
  }

  @DisplayName("Test winning point rolls")
  @ParameterizedTest(name = "Roll = {0}, point = {1}")
  @MethodSource("pointWinProvider")
  void testPointWin(int roll, int point) {
    assertSame(State.WIN, State.POINT.roll(roll, point));
  }

  @DisplayName("Test losing point rolls")
  @ParameterizedTest(name = "Roll = {0}, point = {1}")
  @MethodSource("pointLossProvider")
  void testPointLoss(int roll, int point) {
    assertSame(State.LOSS, State.POINT.roll(roll, point));
  }

  static Stream<Arguments> pointPointProvider() {
    Builder<Arguments> builder = Stream.builder();
    for (int roll = 2; roll <= 12; roll++) {
      if (roll != 7) {
        for (int point : new int[]{4, 5, 6, 8, 9, 10}) {
          if (point != roll) {
            builder.add(Arguments.arguments(roll, point));
          }
        }
      }
    }
    return builder.build();
  }

  static Stream<Arguments> pointWinProvider() {
    Builder<Arguments> builder = Stream.builder();
    for (int point : new int[]{4, 5, 6, 8, 9, 10}) {
      builder.add(Arguments.arguments(point, point));
    }
    return builder.build();
  }

  static Stream<Arguments> pointLossProvider() {
    Builder<Arguments> builder = Stream.builder();
    for (int point : new int[]{4, 5, 6, 8, 9, 10}) {
      builder.add(Arguments.arguments(7, point));
    }
    return builder.build();
  }

}

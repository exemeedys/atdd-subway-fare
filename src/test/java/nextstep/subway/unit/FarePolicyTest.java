package nextstep.subway.unit;

import nextstep.subway.domain.discount.DiscountCalculator;
import nextstep.subway.domain.fare.BasicFarePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FarePolicyTest {

    @DisplayName("요금 계산 - 10km 이하")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void calculateOverFareFromBasic(int distance) {

        // when
        BasicFarePolicy basicFarePolicy = new BasicFarePolicy();
        long fare = basicFarePolicy.calculateOverFare(distance);

        // then
        assertThat(fare).isEqualTo(1250);
    }

    @DisplayName("요금 계산 - 10km 초과 50km 이하")
    @ParameterizedTest
    @MethodSource("calculateFromFirstApplyToFare")
    void calculateOverFareFromFirst(int distance, long expectedFare) {

        // when
        BasicFarePolicy basicFarePolicy = new BasicFarePolicy();
        long fare = basicFarePolicy.calculateOverFare(distance);

        // then
        assertThat(fare).isEqualTo(expectedFare);
    }

    private static Stream<Arguments> calculateFromFirstApplyToFare() {
        return Stream.of(
                Arguments.arguments(11, 1350),
                Arguments.arguments(16, 1450),
                Arguments.arguments(21, 1550),
                Arguments.arguments(26, 1650),
                Arguments.arguments(31, 1750),
                Arguments.arguments(36, 1850),
                Arguments.arguments(41, 1950),
                Arguments.arguments(45, 1950),
                Arguments.arguments(46, 2050),
                Arguments.arguments(50, 2050)
        );
    }


    @DisplayName("요금 계산 - 50km 이상")
    @Test
    void calculateOverFareFromSecond() {
        // given
        final int distance = 59;

        // when
        BasicFarePolicy basicFarePolicy = new BasicFarePolicy();
        long fare = basicFarePolicy.calculateOverFare(distance);

        // then
        assertThat(fare).isEqualTo(2250);
    }

    @DisplayName("요금 계산 - 어린이 할인 정책 적용")
    @ParameterizedTest
    @MethodSource("calculateApplyToDiscountChildFare")
    void calculateOverFareApplyDiscountChildren(int distance, long expectedFare) {
        // given
        BasicFarePolicy basicFarePolicy = new BasicFarePolicy();
        long fare = basicFarePolicy.calculateOverFare(distance);

        // when
        long discountFare = DiscountCalculator.applyToDiscountFare(DiscountCalculator.DiscountPolicy.CHILDREN, fare);

        // then
        assertThat(discountFare).isEqualTo(expectedFare);
    }

    private static Stream<Arguments> calculateApplyToDiscountChildFare() {
        return Stream.of(
                Arguments.arguments(11, 850),
                Arguments.arguments(16, 900),
                Arguments.arguments(21, 950),
                Arguments.arguments(26, 1000),
                Arguments.arguments(31, 1050),
                Arguments.arguments(36, 1100),
                Arguments.arguments(41, 1150),
                Arguments.arguments(45, 1150),
                Arguments.arguments(46, 1200),
                Arguments.arguments(50, 1200)
        );
    }

    @DisplayName("요금 계산 - 청소년 할인 정책 적용")
    @ParameterizedTest
    @MethodSource("calculateApplyToDiscountTeenagerFare")
    void calculateOverFareApplyDiscountTeenager(int distance, long expectedFare) {

        // given
        BasicFarePolicy basicFarePolicy = new BasicFarePolicy();
        long fare = basicFarePolicy.calculateOverFare(distance);

        // when
        long discountFare = DiscountCalculator.applyToDiscountFare(DiscountCalculator.DiscountPolicy.TEENAGER, fare);

        // then
        assertThat(discountFare).isEqualTo(expectedFare);
    }


    private static Stream<Arguments> calculateApplyToDiscountTeenagerFare() {
        return Stream.of(
                Arguments.arguments(11, 1150),
                Arguments.arguments(16, 1230),
                Arguments.arguments(21, 1310),
                Arguments.arguments(26, 1390),
                Arguments.arguments(31, 1470),
                Arguments.arguments(36, 1550),
                Arguments.arguments(41, 1630),
                Arguments.arguments(45, 1630),
                Arguments.arguments(46, 1710),
                Arguments.arguments(50, 1710)
        );
    }
}

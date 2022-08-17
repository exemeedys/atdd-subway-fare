package nextstep.subway.domain.fare;

import nextstep.subway.domain.discount.DiscountPolicyByAgeCalculator;

public class DiscountByAgePolicy implements FarePolicy {

    private Target target;

    public DiscountByAgePolicy(final Target target) {
        this.target = target;
    }

    public Target getTarget() {
        return this.target;
    }
    @Override
    public long calculateOverFare(Target target, long fare) {
        TargetByAge targetByAge = (TargetByAge) target;
        return DiscountPolicyByAgeCalculator.applyToDiscountFare(targetByAge.getMember(), fare);
    }


}

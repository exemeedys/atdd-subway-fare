package nextstep.subway.domain.fare;

import nextstep.member.domain.Member;
import nextstep.subway.domain.Sections;

public interface FarePolicy {
    long calculateOverFare(Target target, long fare);
    Target getTarget();
}

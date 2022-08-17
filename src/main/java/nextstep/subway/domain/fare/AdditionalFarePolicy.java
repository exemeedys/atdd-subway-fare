package nextstep.subway.domain.fare;

import nextstep.subway.domain.Section;
import nextstep.subway.domain.Sections;

import javax.naming.OperationNotSupportedException;
import java.util.Comparator;
import java.util.List;

public class AdditionalFarePolicy implements FarePolicy {

    private Target target;

    public AdditionalFarePolicy(Target target) {
        this.target = target;
    }

    public Target getTarget() {
        return this.target;
    }

    @Override
    public long calculateOverFare(Target target, long fare) {
        Sections sections = (Sections) target;
        List<Section> findSections = sections.getSections();
        return fare + findSections.stream()
                .map(s -> s.getLine())
                .distinct()
                .map(l -> l.getFare().getValue())
                .max(Comparator.naturalOrder())
                .orElse(0);
    }
}

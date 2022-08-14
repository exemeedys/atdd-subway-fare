package nextstep.subway.domain.path;

import nextstep.subway.domain.Sections;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.fare.AdditionalFarePolicy;
import nextstep.subway.domain.fare.BasicFarePolicy;
import nextstep.subway.domain.fare.FarePolicy;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Path {
    private Sections sections;
    private List<FarePolicy> farePolicyListener;

    public Path(Sections sections) {
        this.sections = sections;
        this.farePolicyListener = List.of(new BasicFarePolicy(), new AdditionalFarePolicy());
    }

    public Sections getSections() {
        return sections;
    }

    public int extractDistance() {
        return sections.totalDistance();
    }

    public int extractDuration() {
        return sections.totalDuration();
    }

    public long calculateFare() {
        long fare = 0L;
        for(FarePolicy farePolicy : farePolicyListener) {
            fare += farePolicy.calculateOverFare(sections);
        }
        return fare;
    }

    public List<Station> getStations() {
        return sections.getStations();
    }
}

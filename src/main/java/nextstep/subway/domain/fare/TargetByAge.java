package nextstep.subway.domain.fare;

import nextstep.member.domain.Member;

public class TargetByAge implements Target {

    private Member member;
    private long fare;

    public TargetByAge(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return this.member;
    }

    public long getFare() {
        return this.fare;
    }
}

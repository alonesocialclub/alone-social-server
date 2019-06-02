package social.alone.server.event.type;

public enum EventQueryType {
    /*
     * 내가 만든 이벤트
     * */
    OWNER,

    /*
     * 내가 참가신청한 이벤트
     * */
    JOINER,

    /**
     * 내가 만들거나 참여중인것
     */
    MY,

    /*
     * 모든
     * */
    ALL
}

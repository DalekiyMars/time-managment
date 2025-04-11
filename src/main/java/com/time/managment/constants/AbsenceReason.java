package com.time.managment.constants;

import lombok.Getter;

@Getter
public enum AbsenceReason {
    UNPAID_LEAVE("unpaid_leave"),
    WORK_LEAVE("work_leave"),
    VACATION("vacation");

    private final String value;

    AbsenceReason(String value) {
        this.value = value;
    }

    public static boolean isValidReason(AbsenceReason reason) {
        for (AbsenceReason r : AbsenceReason.values()) {
            if (r == reason) {
                return true;
            }
        }
        return false;
    }

    public static AbsenceReason fromString(String value) {
        for (AbsenceReason reason : AbsenceReason.values()) {
            if (reason.getValue().equals(value)) {
                return reason;
            }
        }
        throw new IllegalArgumentException("Unknown reason: " + value);
    }
}

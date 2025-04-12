package com.time.managment.constants;

import lombok.Getter;

@Getter
public enum AbsenceReason {
    UNPAID_LEAVE("unpaid_leave", "Отгул за свой счёт"),
    WORK_LEAVE("work_leave", "Командировка"),
    VACATION("vacation", "Отпуск");

    private final String value;
    private final String displayName;

    AbsenceReason(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
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
            if (reason.getValue().equalsIgnoreCase(value)) {
                return reason;
            }
        }
        throw new IllegalArgumentException("Unknown reason: " + value);
    }
}

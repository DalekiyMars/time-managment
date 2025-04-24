package com.time.managment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "user_departments")
@Accessors(chain = true)
public class UserDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_timesheet", referencedColumnName = "timesheet")
    private SecurityUser user;

    @Column(name = "department_number", nullable = false)
    private Integer departmentNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDepartment that = (UserDepartment) o;

        if (!Objects.equals(departmentNumber, that.departmentNumber))
            return false;

        return user != null && that.user != null &&
                user.getTimesheet() != null &&
                user.getTimesheet().equals(that.user.getTimesheet());
    }

    @Override
    public int hashCode() {
        int result = user != null && user.getTimesheet() != null ? user.getTimesheet().hashCode() : 0;
        result = 31 * result + (departmentNumber != null ? departmentNumber.hashCode() : 0);
        return result;
    }
}

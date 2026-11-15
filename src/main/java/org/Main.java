package org;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {
    static void main() {
    }

    static void date_difference(){
        LocalDate startDate = LocalDate.of(2026, 1, 10);
        LocalDate endDate = LocalDate.of(2026, 1, 10);

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        System.out.println(days);

    }
}

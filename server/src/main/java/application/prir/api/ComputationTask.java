package application.prir.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
class ComputationTask {

    private static Random random = new Random();
    private static double min = -100;
    private static double max = 100;
    private int id;
    private double[][] matrix;
    private double[] array;
    private double[] resolved;
    private LocalDateTime startTime;

    ComputationTask(int id, int n) {
        this.id = id;
        double[][] randomMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            randomMatrix[i] = random.doubles(n, min, max).toArray();
        }
        this.matrix = randomMatrix;
        this.array = random.doubles(n).toArray();
        this.resolved = new double[n];
    }
}

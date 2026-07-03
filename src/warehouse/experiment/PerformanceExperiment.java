package warehouse.experiment;

import java.time.LocalDate;
import warehouse.model.BatchArrayList;
import warehouse.model.ProductBatch;

public class PerformanceExperiment {

    private static final int WARM_UP_TIMES = 3;
    private static final int TRIAL_TIMES = 5;
    private static volatile long blackhole;

    public static void main(String[] args) {

        int[] inputSizes = {
                100,
                500,
                1000,
                5000,
                10000
        };

        System.out.printf(
                "%-10s %-15s %-15s %-15s %-15s %-15s %-15s%n",
                "Size",
                "Array Add",
                "Linked Add",
                "Array Remove",
                "Linked Remove",
                "Array Traverse",
                "Linked Traverse");

        for (int size : inputSizes) {

            ProductBatch[] dataset = createDataset(size);

            double arrayAddTime = measureArrayAdd(dataset);
            double linkedAddTime = measureLinkedAdd(dataset);
            double arrayRemoveTime = measureArrayRemoveMiddle(dataset);
            double linkedRemoveTime = measureLinkedRemoveMiddle(dataset);
            double arrayTraverseTime = measureArrayTraversal(dataset);
            double linkedTraverseTime = measureLinkedTraversal(dataset);

            System.out.printf(
                    "%-10d %-15.6f %-15.6f %-15.6f %-15.6f %-15.6f %-15.6f%n",
                    size,
                    arrayAddTime,
                    linkedAddTime,
                    arrayRemoveTime,
                    linkedRemoveTime,
                    arrayTraverseTime,
                    linkedTraverseTime);
        }
    }

    private static ProductBatch[] createDataset(int size) {

        ProductBatch[] dataset = new ProductBatch[size];

        LocalDate importDate = LocalDate.now();
        LocalDate expiryDate = importDate.plusMonths(6);

        for (int i = 0; i < size; i++) {

            dataset[i] = new ProductBatch(
                    "BATCH" + i,
                    "PRODUCT" + (i % 100),
                    10,
                    importDate,
                    expiryDate);
        }

        return dataset;
    }

    private static BatchArrayList createArrayList(ProductBatch[] dataset) {

        BatchArrayList list = new BatchArrayList();

        for (ProductBatch batch : dataset) {
            list.add(batch);
        }

        return list;
    }

    private static BatchLinkedList createLinkedList(ProductBatch[] dataset) {

        BatchLinkedList list = new BatchLinkedList();

        for (ProductBatch batch : dataset) {
            list.addLast(batch);
        }

        return list;
    }

    private static double measureArrayAdd(ProductBatch[] dataset) {

        long totalTime = 0;

        for (int trial = -WARM_UP_TIMES; trial < TRIAL_TIMES; trial++) {

            BatchArrayList list = new BatchArrayList();

            long startTime = System.nanoTime();

            for (ProductBatch batch : dataset) {
                list.add(batch);
            }

            long endTime = System.nanoTime();
            blackhole += list.size();

            if (trial >= 0) {
                totalTime += endTime - startTime;
            }
        }

        return toMilliseconds(totalTime / (double) TRIAL_TIMES);
    }

    private static double measureLinkedAdd(ProductBatch[] dataset) {

        long totalTime = 0;

        for (int trial = -WARM_UP_TIMES; trial < TRIAL_TIMES; trial++) {

            BatchLinkedList list = new BatchLinkedList();

            long startTime = System.nanoTime();

            for (ProductBatch batch : dataset) {
                list.addLast(batch);
            }

            long endTime = System.nanoTime();
            blackhole += list.traverseAndSumQuantity();

            if (trial >= 0) {
                totalTime += endTime - startTime;
            }
        }

        return toMilliseconds(totalTime / (double) TRIAL_TIMES);
    }

    private static double measureArrayRemoveMiddle(ProductBatch[] dataset) {

        long totalTime = 0;

        for (int trial = -WARM_UP_TIMES; trial < TRIAL_TIMES; trial++) {

            BatchArrayList list = createArrayList(dataset);
            int middleIndex = list.size() / 2;

            long startTime = System.nanoTime();

            ProductBatch removed = list.remove(middleIndex);

            long endTime = System.nanoTime();
            blackhole += removed == null ? 0 : removed.getQuantity();

            if (trial >= 0) {
                totalTime += endTime - startTime;
            }
        }

        return toMilliseconds(totalTime / (double) TRIAL_TIMES);
    }

    private static double measureLinkedRemoveMiddle(ProductBatch[] dataset) {

        long totalTime = 0;

        for (int trial = -WARM_UP_TIMES; trial < TRIAL_TIMES; trial++) {

            BatchLinkedList list = createLinkedList(dataset);
            int middleIndex = dataset.length / 2;

            long startTime = System.nanoTime();

            ProductBatch removed = list.removeAt(middleIndex);

            long endTime = System.nanoTime();
            blackhole += removed == null ? 0 : removed.getQuantity();

            if (trial >= 0) {
                totalTime += endTime - startTime;
            }
        }

        return toMilliseconds(totalTime / (double) TRIAL_TIMES);
    }

    private static double measureArrayTraversal(ProductBatch[] dataset) {

        long totalTime = 0;

        BatchArrayList list = createArrayList(dataset);

        for (int trial = -WARM_UP_TIMES; trial < TRIAL_TIMES; trial++) {

            long startTime = System.nanoTime();

            long result = traverseArrayList(list);

            long endTime = System.nanoTime();
            blackhole += result;

            if (trial >= 0) {
                totalTime += endTime - startTime;
            }
        }

        return toMilliseconds(totalTime / (double) TRIAL_TIMES);
    }

    private static double measureLinkedTraversal(ProductBatch[] dataset) {

        long totalTime = 0;

        BatchLinkedList list = createLinkedList(dataset);

        for (int trial = -WARM_UP_TIMES; trial < TRIAL_TIMES; trial++) {

            long startTime = System.nanoTime();

            long result = list.traverseAndSumQuantity();

            long endTime = System.nanoTime();
            blackhole += result;

            if (trial >= 0) {
                totalTime += endTime - startTime;
            }
        }

        return toMilliseconds(totalTime / (double) TRIAL_TIMES);
    }

    private static long traverseArrayList(BatchArrayList list) {

        long totalQuantity = 0;

        for (int i = 0; i < list.size(); i++) {

            ProductBatch batch = list.get(i);
            totalQuantity += batch.getQuantity();
        }

        return totalQuantity;
    }

    private static double toMilliseconds(double nanoseconds) {
        return nanoseconds / 1_000_000.0;
    }
}
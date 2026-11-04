
import java.util.*;

class Solution {
    private static final long INF = Long.MAX_VALUE / 4;

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int m = n - x + 1;

        // Step 1: Calculate the cost of every window of size x.
        long[] cost = new long[m];
        MedianWindow window = new MedianWindow();

        for (int i = 0; i < x; i++) {
            window.add(nums[i]);
        }

        cost[0] = window.getCost();

        for (int i = x; i < n; i++) {
            window.remove(nums[i - x]);
            window.add(nums[i]);
            cost[i - x + 1] = window.getCost();
        }

        // Step 2: DP for selecting k non-overlapping windows.
        long[][] dp = new long[k + 1][n + 1];

        for (int t = 0; t <= k; t++) {
            Arrays.fill(dp[t], INF);
        }

        dp[0][0] = 0;

        for (int i = 0; i < n; i++) {
            for (int t = 0; t <= k; t++) {
                if (dp[t][i] == INF) {
                    continue;
                }

                // Option 1: Skip the current position.
                dp[t][i + 1] = Math.min(
                    dp[t][i + 1], dp[t][i]
                );

                // Option 2: Select a window starting at i.
                if (t < k && i + x <= n) {
                    dp[t + 1][i + x] = Math.min(
                        dp[t + 1][i + x],
                        dp[t][i] + cost[i]
                    );
                }
            }
        }

        return dp[k][n];
    }

    // Maintains the median and the sum of absolute differences.
    static class MedianWindow {
        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        int leftSize = 0;
        int rightSize = 0;

        long leftSum = 0;
        long rightSum = 0;

        void add(int value) {
            if (left.isEmpty() || value <= left.lastKey()) {
                insert(left, value);
                leftSize++;
                leftSum += value;
            } else {
                insert(right, value);
                rightSize++;
                rightSum += value;
            }

            balance();
        }

        void remove(int value) {
            if (left.containsKey(value)) {
                erase(left, value);
                leftSize--;
                leftSum -= value;
            } else {
                erase(right, value);
                rightSize--;
                rightSum -= value;
            }

            balance();
        }

        void balance() {
            while (leftSize > rightSize + 1) {
                int value = left.lastKey();

                erase(left, value);
                leftSize--;
                leftSum -= value;

                insert(right, value);
                rightSize++;
                rightSum += value;
            }

            while (leftSize < rightSize) {
                int value = right.firstKey();

                erase(right, value);
                rightSize--;
                rightSum -= value;

                insert(left, value);
                leftSize++;
                leftSum += value;
            }
        }

        long getCost() {
            long median = left.lastKey();

            long leftCost =
                median * leftSize - leftSum;

            long rightCost =
                rightSum - median * rightSize;

            return leftCost + rightCost;
        }

        private void insert(
            TreeMap<Integer, Integer> map, int value
        ) {
            map.put(value, map.getOrDefault(value, 0) + 1);
        }

        private void erase(
            TreeMap<Integer, Integer> map, int value
        ) {
            int count = map.get(value);

            if (count == 1) {
                map.remove(value);
            } else {
                map.put(value, count - 1);
            }
        }
    }
}

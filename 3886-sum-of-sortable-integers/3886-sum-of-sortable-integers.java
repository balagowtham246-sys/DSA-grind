
class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;

        int[] prefMax = new int[n + 1];
        int[] suffMin = new int[n + 1];
        int[] prefDrop = new int[n];

        // Prefix maximum
        for (int i = 0; i < n; i++) {
            prefMax[i + 1] = Math.max(prefMax[i], nums[i]);
        }

        // Suffix minimum
        suffMin[n] = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            suffMin[i] = Math.min(nums[i], suffMin[i + 1]);
        }

        // Count adjacent decreasing pairs
        for (int i = 1; i < n; i++) {
            prefDrop[i] = prefDrop[i - 1]
                    + (nums[i - 1] > nums[i] ? 1 : 0);
        }

        int ans = 0;

        // Enumerate divisors of n
        for (int d = 1; d <= n / d; d++) {
            if (n % d == 0) {
                if (check(nums, d, prefMax, suffMin, prefDrop)) {
                    ans += d;
                }

                int other = n / d;

                if (other != d &&
                    check(nums, other, prefMax, suffMin, prefDrop)) {
                    ans += other;
                }
            }
        }

        return ans;
    }

    private boolean check(
            int[] nums, int k,
            int[] prefMax, int[] suffMin, int[] prefDrop) {

        int n = nums.length;

        for (int start = 0; start < n; start += k) {
            int end = start + k - 1;

            // Count decreasing pairs inside this block
            int drops = prefDrop[end] - prefDrop[start];

            // Include the circular boundary
            if (nums[end] > nums[start]) {
                drops++;
            }

            // Block must be cyclically sortable
            if (drops > 1) {
                return false;
            }

            // Previous elements must not exceed remaining elements
            if (prefMax[start] > suffMin[start]) {
                return false;
            }
        }

        return true;
    }
}

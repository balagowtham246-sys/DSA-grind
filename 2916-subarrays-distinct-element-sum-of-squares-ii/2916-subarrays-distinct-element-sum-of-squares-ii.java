
class Solution {
    static final long MOD = 1_000_000_007L;

    int n;
    long[] sum, sumSq, lazy;

    void apply(int node, int left, int right, long delta) {
        long oldSum = sum[node];
        long len = right - left + 1L;

        sumSq[node] = (
            sumSq[node]
            + 2L * delta % MOD * oldSum % MOD
            + delta * delta % MOD * len % MOD
        ) % MOD;

        sum[node] = (oldSum + delta * len) % MOD;
        lazy[node] += delta;
    }

    void pushDown(int node, int left, int right) {
        if (lazy[node] == 0 || left == right) {
            return;
        }

        int mid = left + (right - left) / 2;

        apply(node * 2, left, mid, lazy[node]);
        apply(node * 2 + 1, mid + 1, right, lazy[node]);

        lazy[node] = 0;
    }

    void update(int node, int left, int right,
                int ql, int qr) {
        if (ql > right || qr < left) {
            return;
        }

        if (ql <= left && right <= qr) {
            apply(node, left, right, 1);
            return;
        }

        pushDown(node, left, right);

        int mid = left + (right - left) / 2;

        update(node * 2, left, mid, ql, qr);
        update(node * 2 + 1, mid + 1, right, ql, qr);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

        sumSq[node] = (
            sumSq[node * 2] + sumSq[node * 2 + 1]
        ) % MOD;
    }

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n];
        sumSq = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        long ans = 0;

        for (int r = 0; r < n; r++) {
            int x = nums[r];

            // Only these starting positions gain
            // one new distinct element.
            int left = last[x] + 1;

            update(1, 0, n - 1, left, r);

            // Sum of squares for all subarrays
            // ending at index r.
            ans = (ans + sumSq[1]) % MOD;

            last[x] = r;
        }

        return (int) ans;
    }
}

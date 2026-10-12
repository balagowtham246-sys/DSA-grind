class Solution {
public int maxSum(List<Integer> nums, int k) {
int MOD = 1_000_000_007;
int[] count = new int[31];


    // Count how many numbers contain each bit
    for (int num : nums) {
        for (int bit = 0; bit < 31; bit++) {
            if (((num >> bit) & 1) == 1) {
                count[bit]++;
            }
        }
    }

    long ans = 0;

    // Construct the largest possible numbers
    for (int i = 0; i < k; i++) {
        int num = 0;

        for (int bit = 0; bit < 31; bit++) {
            if (count[bit] > 0) {
                num |= (1 << bit);
                count[bit]--;
            }
        }

        ans = (ans + (long) num * num) % MOD;
    }

    return (int) ans;
}


}

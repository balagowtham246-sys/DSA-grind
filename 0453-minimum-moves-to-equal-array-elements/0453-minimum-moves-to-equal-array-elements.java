import java.util.Arrays;

class Solution {
    public int minMoves(int[] nums) {
        int min = nums[0];
        long sum = 0; // Use long to prevent integer overflow during accumulation
        
        for (int num : nums) {
            sum += num;
            if (num < min) {
                min = num;
            }
        }
        
        return (int)(sum - (long)nums.length * min);
    }
}

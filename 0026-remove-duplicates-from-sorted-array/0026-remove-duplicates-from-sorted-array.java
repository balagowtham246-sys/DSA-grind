class Solution {
    public int removeDuplicates(int[] nums) {
        int count = 0;
      
        int n = nums.length;
        int i=0;
        int j = 1;
        while(i<n && j<n){
            if(nums[i] != nums[j]){
            i++;
              nums[i] = nums[j];  
            }
            j++;
        }
        return i+1;
    }
}
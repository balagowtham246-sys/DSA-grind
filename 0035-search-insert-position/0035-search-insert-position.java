class Solution {
    public int searchInsert(int[] nums, int target) {
        int pos = 0;
        int found = 0;
        for(int i=0;i<nums.length;i++){

            if(target==nums[i]){
                pos = i;
                found = 1;
                break;
            }
            else if(target < nums[i]){
                pos = i;
                found = 1;
                break;
            }

        if(found == 0){
            pos = nums.length;
        }

        }
        return pos;
        
    }
}
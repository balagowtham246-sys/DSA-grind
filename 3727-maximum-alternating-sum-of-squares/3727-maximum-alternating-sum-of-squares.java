class Solution {
    public long maxAlternatingSum(int[] nums) {
        long sum = 0;
        long[] sqr = new long[nums.length];
        for(int i=0;i<nums.length;i++){
            sqr[i]=(long)nums[i]*nums[i];
        }
        Arrays.sort(sqr);
      for(int i=0;i<nums.length;i++){
        if(i<nums.length/2){
            sum -= sqr[i];
        }
        else{
            sum += sqr[i];
        }
      }
      

         return sum;
    }
}
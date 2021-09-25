package com.liu.blog;

public class ZhaoYinTest1 {
    public static void main(String[] args) {
        int[] nums = new int[]{1,100};
        int[] values= new int[]{2,1};
        int maxValue = getMaxValue(nums, values);
        System.out.println(maxValue);
    }
    public  static int getMaxValue (int[] nums, int[] values) {
        // write code here
        int index = 0;
        int left  =0;
        int right = nums.length -1;
        int[] dp = new int[nums.length + 1];
        int[][] dp2 = new int[nums.length +1][nums.length + 1];
        dp[0] = 0;
        dp2[0][0] = 0;
        dp2[0][1] = nums[0] * values[0];
        dp2[1][0] = nums[nums.length - 1] * values[0];
        index = 1;
        int col = 1;
        while(index < nums.length){
            while(col < nums.length){
                //left = index, right = length - col
//                dp2[index][col] = Math.max(nums[left] * values[index] ,nums[right] * values[index]) +
//                        Math.max(dp2[index -1][col],dp2[index][col-1]);



                dp2[index][col] = Math.max(dp2[index -1][col]+Math.max(nums[col] * values[index] ,nums[nums.length - col] * values[index]),
                        dp2[index ][col - 1]+Math.max(nums[index] * values[index] ,nums[nums.length - index] * values[index]));
                col++;
            }
            int tempValue = nums[left] * values[index] - nums[right] * values[index];


//            int tempValue = nums[left] * values[index] - nums[right] * values[index];
//            if(tempValue > 0){
//                dp[index + 1] = dp[index] + nums[left] * values[index];
//                left++;
//            }else{
//                dp[index + 1] = dp[index] + nums[right] * values[index];
//                right--;
//            }
            index++;

        }
        System.out.println(dp2[nums.length][nums.length]);
        return dp[nums.length];

    }
}

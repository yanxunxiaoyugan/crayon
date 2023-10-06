package com.liu.blog;

import java.util.*;

public class LeetCode {
    public static void main(String[] args) {
        final int c = "asd".charAt(0);
        LeetCode leetCode = new LeetCode();
        int[][] arr= {{1,2,3},{4,5,6},{7,8,9}};
        Deque<Integer> deque = new ArrayDeque<>();
        List<List<Integer>> list = new ArrayList<>();
        LinkedList<Integer> a = new LinkedList<>();
//        list.add(list);
        a.set(0,1);

        int[] repeatNumber = leetCode.spiralOrder(arr);
        System.out.println(repeatNumber);
    }
    public int findRepeatNumber(int[] nums) {

        for(int i =0 ; i < nums.length; i++){
            while(nums[i] != i){
                if(nums[i] == nums[nums[i]]){
                    return nums[i];
                }
                int temp = nums[nums[i]];
                nums[nums[i]] = nums[i];
                nums[i] = temp;

            }

        }
        return -1;
    }

    public int[] spiralOrder(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return new int[]{};
        }
        int count = matrix.length * matrix[0].length;
        int left =0;
        int right = matrix[0].length - 1;
        int top = 0;
        int down = matrix.length  -1 ;

        int[] res = new int[count];
        int curCount = 0;
        int rowNum = 0;
        int colNum = 0;
        while(curCount < count){

            while(curCount < count && colNum < right){
                res[curCount] = matrix[rowNum][colNum];
                curCount++;
                colNum++;
            }
            while(curCount < count && rowNum < down){
                res[curCount] = matrix[rowNum][colNum];
                curCount++;
                rowNum++;
            }
            while(curCount < count && colNum > left){
                res[curCount] = matrix[rowNum][colNum];
                curCount++;
                colNum--;
            }
            while(curCount < count && rowNum > top){
                res[curCount] = matrix[rowNum][colNum];
                curCount++;
                rowNum--;
            }
            while(curCount < count && top == down && left <= right){
                res[curCount] = matrix[rowNum][colNum];
                curCount++;
                left++;
                colNum++;
            }
            while(curCount < count && left == right && top <= down){
                res[curCount] = matrix[rowNum][colNum];
                curCount++;
                top++;
                rowNum++;
            }
            left++;
            right--;
            top++;
            down--;
            colNum++;
            rowNum++;
        }
        return res;
    }
}

package com.liu.test.alg;

import java.util.Arrays;

public class SortArray {
    public static void main(String[] args) {
        SortArray solution = new SortArray();
        int[] arr = new int[]{3,2,4,4,5,2,4,7};
        solution.sortArray(arr);
        for(int i : arr){
            System.out.print(i+" ");
        }
    }
    public int[] sortArray(int[] nums) {
        if(nums == null || nums.length == 1){
            return nums;
        }
        int[] newNums = Arrays.copyOf(nums, nums.length);
        quickSortReview(nums,0,nums.length - 1);
//        quickSort(newNums,0,newNums.length - 1);
        return nums;
    }

    private void quickSort(int[] nums, int left, int right){
        if(left >= right){
            return ;
        }
        int[] indexs = partition(nums,left,right);
        quickSort(nums,left,indexs[0] -1);
        quickSort(nums,indexs[1] ,right);
    }
    private int[] partition(int[] nums, int left, int right){
        int[] res = new int[2];
        int low = left;
        int high = right;
        int current = left;
        int temp = nums[right];
        while(current <= high){
            if(nums[current] > temp){
                swap(nums,current,high);
                high--;
            }else if(nums[current] < temp){
                swap(nums,current,low);
                current++;
                low++;
            }else{
                current++;
            }
        }
//        swap(nums,right,high);
        res[0] = low ;
        res[1] = high ;
        System.out.print("qucik:");
        for(int i : nums){
            System.out.print(i+" ");
        }
        System.out.println(" low:"+low+" high:"+high);
        return res;

    }

    private void swap(int[] nums,int m,int n){
        int temp = nums[m];
        nums[m] = nums[n];
        nums[n] = temp;
    }

    /**
     * [left,right]
     * @param nums
     * @param left
     * @param right
     */
    public  void quickSortReview(int[] nums, int left, int right){
        if(left >= right){
            return ;
        }
        int[] indexs = partitionReview(nums,left,right);
        quickSortReview(nums,left,indexs[0] -1);
        quickSortReview(nums,indexs[1],right);

    }

    /**
     * [left]
     * @param nums
     * @param left
     * @param right
     * @return
     */
    public int[] partitionReview(int[] nums, int left, int right){
        int low = left;
        int high = right;
        int temp = nums[right];
        int currentIndex = left;

        while(currentIndex <= high){
            if(nums[currentIndex] > temp){
                swap(nums,currentIndex,high);
                high--;
            }else if(nums[currentIndex] < temp){
                swap(nums,currentIndex,low);
                currentIndex++;
                low++;
            }else{
                currentIndex++;
            }
        }
        System.out.print("reivew:");
        for(int i : nums){
            System.out.print(i+" ");
        }
        System.out.println(" low:"+low+" high:"+high);
       return new int[]{low,high };
    }
}
